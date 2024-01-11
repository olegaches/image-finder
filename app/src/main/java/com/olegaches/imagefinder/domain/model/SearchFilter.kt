package com.olegaches.imagefinder.domain.model

import com.olegaches.imagefinder.domain.enums.ImageSize
import com.olegaches.imagefinder.domain.enums.ImageType

class SearchFilter(
    val size: ImageSize = ImageSize.ANY_SIZE,
    val type: ImageType = ImageType.ANY_TYPE
) {
    override fun toString(): String {
        val size = size
        val type = type
        val stringBuilder = StringBuilder()
        if(size != ImageSize.ANY_SIZE) {
            stringBuilder.append("isz:")
            when(size) {
                ImageSize.ICON -> stringBuilder.append("i")
                ImageSize.LARGE -> stringBuilder.append("l")
                ImageSize.MEDIUM -> stringBuilder.append("m")
                else -> {}
            }
        }
        if(type != ImageType.ANY_TYPE) {
            if(stringBuilder.isNotEmpty()) {
                stringBuilder.append("%2C")
            }
            stringBuilder.append("itp:")
            when(type) {
                ImageType.CLIP_ART -> stringBuilder.append("clipart")
                ImageType.LINE_DRAWING -> stringBuilder.append("lineart")
                ImageType.GIF -> stringBuilder.append("animated")
                else -> {}
            }
        }
        return stringBuilder.toString()
    }
}