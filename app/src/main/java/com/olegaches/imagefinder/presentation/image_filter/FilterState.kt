package com.olegaches.imagefinder.presentation.image_filter

import com.olegaches.imagefinder.domain.enums.ImageType
import com.olegaches.imagefinder.domain.enums.ImageSize

data class FilterState(
    val size: ImageSize = ImageSize.ANY_SIZE,
    val type: ImageType = ImageType.ANY_TYPE
)
