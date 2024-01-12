package com.olegaches.imagefinder.presentation.image_filter

import com.olegaches.imagefinder.domain.enums.ImageSize
import com.olegaches.imagefinder.domain.enums.ImageType

sealed interface FilterEvent {
    data object ApplyFilter : FilterEvent
    data class ChangeSize(val size: ImageSize): FilterEvent
    data class ChangeType(val type: ImageType): FilterEvent
}
