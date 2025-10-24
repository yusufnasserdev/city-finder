package dev.yunas.data.repository

import dev.yunas.data.dto.CityDto
import dev.yunas.data.dto.paging.PageResponse
import dev.yunas.data.mapper.toDomain
import dev.yunas.data.service.CitiesApiService
import dev.yunas.data.util.mapAsync
import dev.yunas.data.util.safeApiCall
import dev.yunas.domain.entity.City
import dev.yunas.domain.repository.CityRepository
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Single

@Single(binds = [CityRepository::class])
class CityRepositoryImpl(
    @Provided private val citiesApiService: CitiesApiService
) : CityRepository {
    override suspend fun searchCity(
        searchPhrase: String,
        pageNumber: Int,
        pageSize: Int
    ): List<City> {
        val response = safeApiCall<PageResponse<CityDto>> {
            citiesApiService.getCities()
        }

        return response.items?.mapAsync { it.toDomain() } ?: emptyList()
    }
}