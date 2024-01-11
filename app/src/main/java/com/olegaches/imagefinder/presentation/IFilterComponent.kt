package com.olegaches.imagefinder.presentation

import kotlinx.coroutines.flow.StateFlow

interface IFilterComponent {
    val state: StateFlow<FilterState>
    fun handleEvent(event: FilterEvent)
}
