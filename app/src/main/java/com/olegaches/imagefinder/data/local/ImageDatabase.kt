package com.olegaches.imagefinder.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.olegaches.imagefinder.data.local.entity.ImageEntity

@Database(
    entities = [ImageEntity::class],
    version = 1
)
abstract class ImageDatabase: RoomDatabase() {
    abstract val imageDao: ImageDao
}