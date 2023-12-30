package com.olegaches.imagefinder.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.olegaches.imagefinder.data.local.ImageDao.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ImageEntity(
    @PrimaryKey
    val id: Int? = null,
    val position: Int,
    val original: String,
    val width: Int,
    val height: Int
)