package com.olegaches.imagefinder.domain.enums

import androidx.annotation.StringRes
import com.olegaches.imagefinder.R

enum class Size(@StringRes value: Int) {
    LARGE(R.string.large),
    MEDIUM(R.string.medium),
    ICON(R.string.icon)
}