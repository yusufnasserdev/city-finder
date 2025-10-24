package dev.yunas.data.dto.paging

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageResponse<T>(
    @SerialName("data")
    val items: List<T>? = emptyList(),
    @SerialName("metadata")
    val metadata: PageMetadata? = null
)