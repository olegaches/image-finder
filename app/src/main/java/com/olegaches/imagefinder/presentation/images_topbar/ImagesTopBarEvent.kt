package com.olegaches.imagefinder.presentation.images_topbar

sealed interface ImagesTopBarEvent {
    data class OnQueryChange(val value: String): ImagesTopBarEvent
    data class OnSearch(val value: String): ImagesTopBarEvent
    data class OnBarActiveChange(val value: Boolean): ImagesTopBarEvent
    data object OnFilterClicked: ImagesTopBarEvent
    data object OnBackIconClick: ImagesTopBarEvent
}
