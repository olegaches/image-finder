package com.olegaches.imagefinder.presentation

import kotlinx.coroutines.flow.StateFlow

interface ITopBarComponent {
    val state: StateFlow<TopBarState>

    fun handleEvent(event: TopBarEvent)
}
