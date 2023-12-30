package com.olegaches.imagefinder.data.remote.dto

import com.squareup.moshi.Json

data class SearchResultsDto(
    @Json(name = "images_results")
    val imagesResults: List<ImageDto>?,
    @Json(name = "serpapi_pagination")
    val pagination: PaginationDto
)