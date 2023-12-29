package com.olegaches.imagefinder.data.remote

import com.olegaches.imagefinder.data.remote.dto.AutocompleteDto
import com.olegaches.imagefinder.data.remote.dto.SearchResultsDto
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
        pageNumber: Int
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
        private const val API_KEY = "039b770c968a981b5f3254fbdc67300552ce56d618717d7d0697d01f1fae0cf5"
    }
}