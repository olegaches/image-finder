package com.olegaches.imagefinder.presentation.image_list

import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.domain.model.SearchFilter
import com.olegaches.imagefinder.presentation.util.ImagePositionalParam

sealed interface ImagesListEvent {
    data class SearchImages(val query: String, val filter: SearchFilter?): ImagesListEvent
    data class OnImageClicked(val index: Int, val image: Image): ImagesListEvent
    data class OnAnimateImage(val imagePosParams: ImagePositionalParam): ImagesListEvent
    data class OnScrollToImage(val index: Int): ImagesListEvent
}
