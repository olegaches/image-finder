package com.olegaches.imagefinder.presentation

import androidx.compose.runtime.Immutable
import com.olegaches.imagefinder.util.UiText

@Immutable
data class TopBarState(
    val query: String = "",
    val searchBarActive: Boolean = false,
    val prevQuery: String = "",
    val loading: Boolean = false,
    val error: UiText? = null,
    val suggestions: List<String> = emptyList()
)
