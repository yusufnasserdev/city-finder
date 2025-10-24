package dev.yunas.data.service

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import dev.yunas.data.dto.CityDto
import dev.yunas.data.dto.paging.PageResponse

interface CitiesApiService {

    @GET("v1/geo/cities")
    suspend fun getCities(
        @Query("offset") pageStartIndex: Int,
        @Query("limit") pageSize: Int,
        @Query("namePrefix") searchPhrase: String
    ): Response<PageResponse<CityDto>>

}