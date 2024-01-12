package com.olegaches.imagefinder.presentation.pager

import com.olegaches.imagefinder.presentation.util.ImagePositionalParam

sealed interface PagerSingleEvent {
    data class OnAnimate(
        val expand: Boolean,
        val imagePositionalParam: ImagePositionalParam
    ): PagerSingleEvent
    data object OnBackClicked: PagerSingleEvent
}
