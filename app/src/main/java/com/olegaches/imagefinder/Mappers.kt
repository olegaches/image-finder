package com.olegaches.imagefinder

import com.olegaches.imagefinder.data.local.entity.ImageEntity
import com.olegaches.imagefinder.data.remote.dto.ImageDto
import com.olegaches.imagefinder.domain.model.Image

fun ImageDto.toImage(): Image {
    return Image(
        uri = this.original,
        id = this.position
    )
}

fun ImageDto.toImageEntity(): ImageEntity {
    return ImageEntity(
        position = position,
        original = original
    )
}

fun ImageEntity.toImage(): Image {
    return Image(
        uri = original,
        id = this.position
    )
}