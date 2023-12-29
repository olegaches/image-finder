package com.olegaches.imagefinder.presentation

sealed interface PagerSingleEvent {
    data class OnAnimate(
        val expand: Boolean,
        val imagePositionalParam: ImagePositionalParam
    ): PagerSingleEvent
    data object OnBackClicked: PagerSingleEvent
}
