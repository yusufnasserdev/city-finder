package dev.yunas.domain.repository

import dev.yunas.domain.entity.City

interface CityRepository {
    suspend fun searchCity(
        searchPhrase: String,
        pageNumber: Int,
        pageSize: Int
    ): List<City>
}