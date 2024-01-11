package com.olegaches.imagefinder.data.remote

import com.olegaches.imagefinder.data.remote.dto.AutocompleteDto
import com.olegaches.imagefinder.data.remote.dto.SearchResultsDto
import com.olegaches.imagefinder.domain.enums.Country
import com.olegaches.imagefinder.domain.enums.EngineType
import com.olegaches.imagefinder.domain.enums.Language
import com.olegaches.imagefinder.domain.model.SearchFilter
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageSearchApi {
    @GET("search")
    suspend fun search(
        @Query("q")
        query: String,
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("engine")
        engine: EngineType = EngineType.GOOGLE_IMAGES,
        @Query("ijn")
        pageNumber: Int,
        @Query("tbs")
        filter: SearchFilter?,
        @Query("gl")
        country: Country?,
        @Query("hl")
        language: Language?
    ): SearchResultsDto

    @GET("search")
    suspend fun autocomplete(
        @Query("q")
        query: String,
        @Query("api_key")
        apiKey: String = API_KEY,
        @Query("engine")
        engine: EngineType = EngineType.GOOGLE_AUTOCOMPLETE,
    ): AutocompleteDto

    companion object {
        const val BASE_URL: String = "https://serpapi.com/"
        private const val API_KEY = "a658e5913c925399055ce22db43e4b1a4d82ef86690fadd40f76ff7f81b02c87"
    }
}