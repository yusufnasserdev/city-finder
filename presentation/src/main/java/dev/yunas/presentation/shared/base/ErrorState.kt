package dev.yunas.presentation.shared.base

sealed class ErrorState {
    object NoInternet : ErrorState()
    object RequestTimeout : ErrorState()
    data class RequestFailed(val message: String? = "Request failed") : ErrorState()
}