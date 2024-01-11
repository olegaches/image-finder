package com.olegaches.imagefinder.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.olegaches.imagefinder.data.local.entity.ImageEntity

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(images: List<ImageEntity>)

    @Query("SELECT * FROM $TABLE_NAME ORDER BY id")
    fun pagingSource(): PagingSource<Int, ImageEntity>

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun clearAll()

    companion object {
        const val TABLE_NAME = "image"
    }
}