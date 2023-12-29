package com.olegaches.imagefinder.presentation

sealed interface ImageListSingleEvent {
    data class OnPagerDismiss(val index: Int): ImageListSingleEvent
}
