package com.olegaches.imagefinder.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val id: Int,
    val uri: String,
    val title: String,
    val source: String,
    val thumbnail: String,
    val width: Int,
    val height: Int
)
