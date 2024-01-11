package com.olegaches.imagefinder.presentation

sealed interface ImageListSingleEvent {
    data class ScrollToImage(val index: Int): ImageListSingleEvent
}
