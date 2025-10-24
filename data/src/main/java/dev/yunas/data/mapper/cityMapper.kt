package dev.yunas.data.mapper

import dev.yunas.data.dto.CityDto
import dev.yunas.domain.entity.City

fun CityDto.toDomain() = City(
    id = id,
    name = name,
    country = country,
    countryCode = countryCode,
    latitude = latitude,
    longitude = longitude
)