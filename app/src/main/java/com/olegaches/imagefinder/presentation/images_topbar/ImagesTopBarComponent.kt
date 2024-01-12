package com.olegaches.imagefinder.presentation.images_topbar

import kotlinx.coroutines.flow.StateFlow

interface ImagesTopBarComponent {
    val state: StateFlow<TopBarState>

    fun handleEvent(event: ImagesTopBarEvent)
}
