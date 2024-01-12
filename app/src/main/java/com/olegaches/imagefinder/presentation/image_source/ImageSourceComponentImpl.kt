package com.olegaches.imagefinder.presentation.image_source

import com.arkivanov.decompose.ComponentContext

class ImageSourceComponentImpl(
    componentContext: ComponentContext,
    override val url: String,
    private val navigateBack: () -> Unit
): ComponentContext by componentContext, ImageSourceComponent {
    override fun onCloseClicked() = navigateBack()
}