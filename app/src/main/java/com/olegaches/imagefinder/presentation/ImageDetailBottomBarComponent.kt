package com.olegaches.imagefinder.presentation

import com.arkivanov.decompose.ComponentContext
import com.olegaches.imagefinder.domain.model.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ImageDetailBottomBarComponent(
    componentContext: ComponentContext,
    image: Image,
    private val navigateToSource: (String) -> Unit,
): ComponentContext by componentContext, IImageDetailBottomBarComponent {
    private val _image = MutableStateFlow<Image?>(image)
    override val image = _image.asStateFlow()
    private val _visible = MutableStateFlow(false)
    override val visible = _visible.asStateFlow()

    init {
        changeVisibility(true)
    }

    override fun updateImage(image: Image?) {
        _image.update { image }
    }

    override fun changeVisibility(visible: Boolean) {
        _visible.update { visible }
    }

    override fun onSourceClicked() {
        image.value?.let { navigateToSource(it.source) }
    }
}