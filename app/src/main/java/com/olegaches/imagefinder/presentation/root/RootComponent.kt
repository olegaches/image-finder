package com.olegaches.imagefinder.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        data class ImagesRootComponent(val component: com.olegaches.imagefinder.presentation.images_root.ImagesRootComponent) : Child
        data class ImageSourceComponent(val component: com.olegaches.imagefinder.presentation.image_source.ImageSourceComponent) : Child
    }
}
