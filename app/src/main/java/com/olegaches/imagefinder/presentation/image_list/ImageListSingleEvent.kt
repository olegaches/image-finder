package com.olegaches.imagefinder.presentation.image_list

sealed interface ImageListSingleEvent {
    data class ScrollToImage(val index: Int): ImageListSingleEvent
}
