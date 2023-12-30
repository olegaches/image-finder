package com.olegaches.imagefinder

import com.olegaches.imagefinder.data.local.entity.ImageEntity
import com.olegaches.imagefinder.data.remote.dto.ImageDto
import com.olegaches.imagefinder.domain.model.Image

fun ImageDto.toImageEntity(): ImageEntity {
    return ImageEntity(
        position = position,
        original = original,
        width = width,
        height = height
    )
}

fun ImageEntity.toImage(): Image {
    return Image(
        uri = original,
        id = this.id!!,
        width = width,
        height = height
    )
}