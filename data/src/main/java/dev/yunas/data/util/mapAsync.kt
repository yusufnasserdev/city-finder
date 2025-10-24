package dev.yunas.data.util

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope


suspend fun <DTO, ENTITY> Collection<DTO>.mapAsync(transform: suspend (DTO) -> ENTITY): List<ENTITY> {
    return coroutineScope {
        map { async { transform(it) } }.awaitAll()
    }
}