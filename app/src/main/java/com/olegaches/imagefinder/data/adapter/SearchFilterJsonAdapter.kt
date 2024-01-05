package com.olegaches.imagefinder.data.adapter

import com.olegaches.imagefinder.domain.enums.ImageType
import com.olegaches.imagefinder.domain.enums.Size
import com.olegaches.imagefinder.domain.model.SearchFilter
import com.squareup.moshi.ToJson

class SearchFilterJsonAdapter {
    @ToJson
    fun toJson(value: SearchFilter): String {
        val (size, type) = value
        val stringBuilder = StringBuilder()
        if(size != null) {
            stringBuilder.append("isz:")
            when(size) {
                Size.ICON -> stringBuilder.append("i")
                Size.LARGE -> stringBuilder.append("l")
                Size.MEDIUM -> stringBuilder.append("m")
            }
        }
        if(type != null) {
            if(stringBuilder.isNotEmpty()) {
                stringBuilder.append("%2C")
            }
            stringBuilder.append("itp:")
            when(type) {
                ImageType.CLIP_ART -> stringBuilder.append("clipart")
                ImageType.LINE_DRAWING -> stringBuilder.append("lineart")
                ImageType.GIF -> stringBuilder.append("animated")
            }
        }
        return stringBuilder.toString()
    }
}