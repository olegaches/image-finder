package com.olegaches.imagefinder.presentation

import com.olegaches.imagefinder.domain.model.Image

sealed interface PagerEvent {
    data class OnAnimateImage(
        val imagePositionalParam: ImagePositionalParam
    ): PagerEvent
    data class OnCurrentImageChanged(val image: Image?): PagerEvent
    data object OnBackClicked: PagerEvent
    data object NavigateBack: PagerEvent
    data class OnScrollTo(val index: Int): PagerEvent
}
