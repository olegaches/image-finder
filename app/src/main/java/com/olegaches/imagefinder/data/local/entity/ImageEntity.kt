package com.olegaches.imagefinder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.olegaches.imagefinder.data.local.ImageDao.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ImageEntity(
    @PrimaryKey
    val id: Int,
    val position: Int,
    val title: String,
    val source: String,
    val original: String,
    val thumbnail: String,
    val width: Int,
    val height: Int
)