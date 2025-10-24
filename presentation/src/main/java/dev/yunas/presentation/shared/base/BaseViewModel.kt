package dev.yunas.presentation.shared.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.yunas.domain.exception.CityFinderException
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

internal abstract class BaseViewModel<State, Effect>(
    initialState: State
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    val effect = _effect.asSharedFlow()

    protected fun updateState(updater: State.() -> State) {
        _state.update { updater(it) }
    }

    protected fun sendEffect(
        effect: Effect,
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            _effect.emit(effect)
        }
    }

    protected fun <R> tryToExecute(
        block: suspend () -> R,
        onSuccess: (R) -> Unit = {},
        onError: (ErrorState) -> Unit = {},
        onStart: () -> Unit = {},
        onEnd: () -> Unit = {},
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        scope: CoroutineScope = viewModelScope,
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            onError(ErrorState.RequestFailed(exception.message))
        }

        return scope.launch(dispatcher + exceptionHandler) {
            onStart()

            runCatching { block() }
                .onSuccess { onSuccess(it) }
                .onFailure {
                    mapExceptionToErrorState(
                        throwable = it,
                        onError = onError
                    )
                }
            onEnd()
        }
    }

    protected fun <R> tryToCollectFlow(
        block: () -> Flow<R>,
        onStart: () -> Unit = {},
        onNewValue: (R) -> Unit,
        onError: (ErrorState) -> Unit,
        onEnd: () -> Unit = {},
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        scope: CoroutineScope = viewModelScope
    ): Job {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            if(exception is kotlinx.coroutines.CancellationException) return@CoroutineExceptionHandler
            onError(ErrorState.RequestFailed(exception.message))
        }

        return scope.launch(dispatcher + exceptionHandler) {
            block()
                .flowOn(dispatcher)
                .onStart { onStart() }
                .onEach { onNewValue(it) }
                .onCompletion { throwable ->
                    throwable?.let {
                        if(throwable is CancellationException) return@onCompletion
                        mapExceptionToErrorState(throwable, onError)
                    } ?: onEnd()
                }
                .catch { throwable -> mapExceptionToErrorState(throwable, onError) }
                .collect()
        }
    }

    private suspend fun mapExceptionToErrorState(
        throwable: Throwable,
        onError: suspend (ErrorState) -> Unit,
    ) {
        logError(throwable)
        val message = throwable.message
        when (throwable) {
            is CityFinderException.NoInternetException -> ErrorState.NoInternet
            else -> ErrorState.RequestFailed(message).also { logError(throwable) }
        }.also { errorState ->
            Napier.e(message = errorState.toString(), tag = LOG_TAG)
        }.let { onError(it) }
    }

    private fun logError(throwable: Throwable) {
        Napier.e(tag = LOG_TAG, message = "${throwable}: ${throwable.message}")
    }

    companion object {
        private const val LOG_TAG = "BaseViewModel"
    }
}