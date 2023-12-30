package com.olegaches.imagefinder.presentation

sealed interface PagerEvent {
    data class OnAnimateImage(
        val imagePositionalParam: ImagePositionalParam
    ): PagerEvent
    data object OnBackClicked: PagerEvent
    data object NavigateBack: PagerEvent
    data class OnScrollTo(val index: Int): PagerEvent
}
