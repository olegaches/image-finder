package com.olegaches.imagefinder.data.remote.dto

import com.squareup.moshi.Json

data class ImageDto(
    @Json(name = "position")
    val position: Int,
    @Json(name = "original")
    val original: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "link")
    val source: String,
    @Json(name = "thumbnail")
    val thumbnail: String,
    @Json(name = "original_width")
    val width: Int,
    @Json(name = "original_height")
    val height: Int
)
