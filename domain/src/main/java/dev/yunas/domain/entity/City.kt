package dev.yunas.domain.entity

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val countryCode: String,
    val latitude: Double,
    val longitude: Double
)
