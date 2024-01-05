package com.olegaches.imagefinder.domain.enums

import androidx.annotation.StringRes
import com.olegaches.imagefinder.R

enum class ImageType(@StringRes typeName: Int) {
    CLIP_ART(R.string.clip_art),
    LINE_DRAWING(R.string.line_drawing),
    GIF(R.string.gif)
}