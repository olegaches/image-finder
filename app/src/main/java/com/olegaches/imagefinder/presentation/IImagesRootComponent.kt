package com.olegaches.imagefinder.presentation

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value

interface IImagesRootComponent {
    val pagerSlot: Value<ChildSlot<*, PagerChild>>
    val sheetSlot: Value<ChildSlot<*, SheetChild>>

    val imagesComponent: IImagesComponent

    fun dismissSheet()

    sealed interface PagerChild {
        data class ImageDetail(val component: ImageDetailComponent) : PagerChild
    }

    sealed interface SheetChild {
        data class Filter(val component: IFilterComponent) : SheetChild
    }
}
