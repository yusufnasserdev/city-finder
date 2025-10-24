package dev.yunas.data.dto.paging

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageMetadata(
    @SerialName("currentOffset")
    val startingOffset: Int? = null,
    @SerialName("totalCount")
    val totalCount: Int? = null,
)
