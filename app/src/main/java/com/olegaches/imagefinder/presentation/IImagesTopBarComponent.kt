package com.olegaches.imagefinder.presentation

import kotlinx.coroutines.flow.StateFlow

interface IImagesTopBarComponent {
    val state: StateFlow<TopBarState>

    fun handleEvent(event: ImagesTopBarEvent)
}
