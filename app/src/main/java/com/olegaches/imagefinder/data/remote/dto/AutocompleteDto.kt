package com.olegaches.imagefinder.data.remote.dto

import com.squareup.moshi.Json

data class AutocompleteDto(
    @Json(name = "suggestions")
    val suggestions: List<SuggestionDto>
)
