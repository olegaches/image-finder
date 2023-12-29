package com.olegaches.imagefinder.presentation

import kotlinx.coroutines.flow.StateFlow

interface IDetailTopBarComponent {

    val state: StateFlow<DetailTopBarState>

    fun changeVisibility(visible: Boolean)

    fun onBackClicked()
}
