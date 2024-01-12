package com.olegaches.imagefinder.presentation.image_detail_bottombar

import com.olegaches.imagefinder.domain.model.Image
import kotlinx.coroutines.flow.StateFlow

interface ImageDetailBottomBarComponent {

    val image: StateFlow<Image?>

    val visible: StateFlow<Boolean>
    fun updateImage(image: Image?)
    fun changeVisibility(visible: Boolean)
    fun onSourceClicked()
}
