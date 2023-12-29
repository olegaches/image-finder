package com.olegaches.imagefinder.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value

interface IImagesRootComponent {
    val childSlot: Value<ChildSlot<*, SlotChild>>

    val imagesComponent: IImagesComponent

    sealed interface SlotChild {
        data class ImageDetail(val component: ImageDetailComponent) : SlotChild
    }
}
