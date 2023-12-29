package com.olegaches.imagefinder.data.remote.dto

import com.squareup.moshi.Json

data class SuggestionDto(
    @Json(name = "value")
    val value: String
)
