package com.olegaches.imagefinder.domain.enums

import androidx.annotation.StringRes
import com.olegaches.imagefinder.R

enum class ImageType(@StringRes val value: Int) {
    ANY_TYPE(R.string.any_type),
    CLIP_ART(R.string.clip_art),
    LINE_DRAWING(R.string.line_drawing),
    GIF(R.string.gif)
}