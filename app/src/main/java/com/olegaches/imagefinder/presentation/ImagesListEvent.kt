package com.olegaches.imagefinder.presentation

import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.domain.model.SearchFilter

sealed interface ImagesListEvent {
    data class SearchImages(val query: String, val filter: SearchFilter?): ImagesListEvent
    data class OnImageClicked(val index: Int, val image: Image): ImagesListEvent
    data class OnAnimateImage(val imagePosParams: ImagePositionalParam): ImagesListEvent
    data class OnScrollToImage(val index: Int): ImagesListEvent
}
