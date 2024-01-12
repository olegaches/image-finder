package com.olegaches.imagefinder.presentation.images_topbar

import androidx.compose.runtime.Immutable
import com.olegaches.imagefinder.domain.util.UiText

@Immutable
data class TopBarState(
    val query: String = "",
    val searchBarActive: Boolean = false,
    val prevQuery: String = "",
    val prevSuggestions: List<String> = emptyList(),
    val loading: Boolean = false,
    val error: UiText? = null,
    val suggestions: List<String> = emptyList()
)
