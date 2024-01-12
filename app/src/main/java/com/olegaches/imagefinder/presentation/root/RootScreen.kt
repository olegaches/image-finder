package com.olegaches.imagefinder.presentation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.olegaches.imagefinder.presentation.image_source.ImageSourceComponentScreen
import com.olegaches.imagefinder.presentation.images_root.ImagesRootComponentScreen

@Composable
fun RootScreen(rootComponent: RootComponent) {
    Children(
        stack = rootComponent.childStack,
        animation = stackAnimation(fade()),
    ) {
        when(val child = it.instance) {
            is RootComponent.Child.ImagesRootComponent -> {
                ImagesRootComponentScreen(child.component)
            }
            is RootComponent.Child.ImageSourceComponent -> {
                ImageSourceComponentScreen(imageSourceComponent = child.component)
            }
        }
    }
}