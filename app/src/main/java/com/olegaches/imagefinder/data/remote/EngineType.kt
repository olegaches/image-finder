package com.olegaches.imagefinder.data.remote

import com.squareup.moshi.Json

enum class EngineType(value: String) {
    @Json(name = "google_autocomplete")
    GOOGLE_AUTOCOMPLETE("google_autocomplete"),
    @Json(name = "google_images")
    GOOGLE_IMAGES("google_images")
}