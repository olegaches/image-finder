package com.olegaches.imagefinder.data.remote.dto

import com.squareup.moshi.Json

data class PaginationDto(
    @Json(name = "current")
    val current: Int,
    @Json(name = "next")
    val next: String?
)
