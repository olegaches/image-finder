package com.olegaches.imagefinder.presentation.image_filter

import kotlinx.coroutines.flow.StateFlow

interface FilterComponent {
    val state: StateFlow<FilterState>
    fun handleEvent(event: FilterEvent)
}
