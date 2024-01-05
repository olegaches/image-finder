package com.olegaches.imagefinder.presentation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface IRootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class ImagesRootComponent(val component: IImagesRootComponent) : Child
        data class ImageSourceComponent(val component: IImageSourceComponent) : Child
    }
}
