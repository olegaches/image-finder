package com.olegaches.imagefinder.presentation

sealed interface TopBarEvent {
    data class OnQueryChange(val value: String): TopBarEvent
    data class OnSearch(val value: String): TopBarEvent
    data class OnBarActiveChange(val value: Boolean): TopBarEvent
    data object OnBackIconClick: TopBarEvent
}
