package com.olegaches.imagefinder.presentation

import com.arkivanov.decompose.ComponentContext

class ImageSourceComponent(
    componentContext: ComponentContext,
    override val url: String,
    private val navigateBack: () -> Unit
): ComponentContext by componentContext, IImageSourceComponent {
    override fun onCloseClicked() = navigateBack()
}