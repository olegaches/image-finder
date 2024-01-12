package com.olegaches.imagefinder.presentation.image_detail_topbar

import kotlinx.coroutines.flow.StateFlow

interface DetailTopBarComponent {

    val state: StateFlow<DetailTopBarState>

    fun changeVisibility(visible: Boolean)

    fun onBackIconClicked()
}
