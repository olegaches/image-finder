package com.olegaches.imagefinder.presentation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation

@Composable
fun RootScreen(rootComponent: IRootComponent) {
    Children(
        stack = rootComponent.childStack,
        animation = stackAnimation(fade()),
    ) {
        when(val child = it.instance) {
            is IRootComponent.Child.ImagesRootComponent -> {
                ImagesRootComponentScreen(child.component)
            }
            is IRootComponent.Child.ImageSourceComponent -> {
                TODO()
            }
        }
    }
}