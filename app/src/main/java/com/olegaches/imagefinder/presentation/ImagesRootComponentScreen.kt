package com.olegaches.imagefinder.presentation

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun ImagesRootComponentScreen(component: IImagesRootComponent) {
    val slotInstance = component.childSlot.subscribeAsState().value.child?.instance
    ImagesComponentScreen(imagesComponent = component.imagesComponent)
    if(slotInstance != null) {
        when(slotInstance) {
            is IImagesRootComponent.SlotChild.ImageDetail -> {
                ImageDetailComponentScreen(imageDetailComponent = slotInstance.component)
            }
        }
    }
}