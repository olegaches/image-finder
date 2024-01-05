package com.olegaches.imagefinder.domain.model

import com.olegaches.imagefinder.domain.enums.ImageType
import com.olegaches.imagefinder.domain.enums.Size

data class SearchFilter(
    val size: Size? = null,
    val type: ImageType? = null
)