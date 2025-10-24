package dev.yunas.data.service

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import dev.yunas.data.dto.CityDto
import dev.yunas.data.dto.paging.PageResponse

interface CitiesApiService {

    @GET("v1/geo/cities")
    suspend fun getCities(): Response<PageResponse<CityDto>>

}