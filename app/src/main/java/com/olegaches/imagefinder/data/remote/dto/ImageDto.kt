package com.olegaches.imagefinder.data.remote.dto

import com.squareup.moshi.Json

data class ImageDto(
    @Json(name = "position")
    val position: Int,
    @Json(name = "original")
    val original: String
)
