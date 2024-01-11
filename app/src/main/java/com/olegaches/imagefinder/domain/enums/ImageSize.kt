package com.olegaches.imagefinder.domain.enums

import androidx.annotation.StringRes
import com.olegaches.imagefinder.R

enum class ImageSize(@StringRes val value: Int) {
    ANY_SIZE(R.string.any_size),
    LARGE(R.string.large),
    MEDIUM(R.string.medium),
    ICON(R.string.icon)
}