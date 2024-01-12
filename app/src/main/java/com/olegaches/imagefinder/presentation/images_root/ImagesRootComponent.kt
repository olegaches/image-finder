package com.olegaches.imagefinder.presentation.images_root

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.olegaches.imagefinder.presentation.image_filter.FilterComponent
import com.olegaches.imagefinder.presentation.image_detail.ImageDetailComponentImpl
import com.olegaches.imagefinder.presentation.images.ImagesComponent

interface ImagesRootComponent {
    val pagerSlot: Value<ChildSlot<*, PagerChild>>
    val sheetSlot: Value<ChildSlot<*, SheetChild>>

    val imagesComponent: ImagesComponent

    fun dismissSheet()

    sealed interface PagerChild {
        data class ImageDetail(val component: ImageDetailComponentImpl) : PagerChild
    }

    sealed interface SheetChild {
        data class Filter(val component: FilterComponent) : SheetChild
    }
}
