package dev.yunas.domain.repository

import dev.yunas.domain.entity.City

interface CityRepository {
    suspend fun searchCity(searchPhrase: String): List<City>
}