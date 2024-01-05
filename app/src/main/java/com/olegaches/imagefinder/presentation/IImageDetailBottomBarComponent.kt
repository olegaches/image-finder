package com.olegaches.imagefinder.presentation

import com.olegaches.imagefinder.domain.model.Image
import kotlinx.coroutines.flow.StateFlow

interface IImageDetailBottomBarComponent {

    val image: StateFlow<Image?>

    val visible: StateFlow<Boolean>
    fun updateImage(image: Image?)
    fun changeVisibility(visible: Boolean)
    fun onSourceClicked()
}
