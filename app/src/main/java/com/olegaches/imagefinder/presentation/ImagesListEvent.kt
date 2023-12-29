package com.olegaches.imagefinder.presentation

sealed interface ImagesListEvent {
    data class SearchImages(val query: String): ImagesListEvent
    data class OnImageClicked(val index: Int): ImagesListEvent
    data class OnAnimateImage(val imagePosParams: ImagePositionalParam): ImagesListEvent
    data class OnScrollToImage(val index: Int): ImagesListEvent
}
