package com.olegaches.imagefinder.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.olegaches.imagefinder.data.local.ImageDatabase
import com.olegaches.imagefinder.data.local.entity.ImageEntity
import com.olegaches.imagefinder.toImageEntity
import java.util.concurrent.atomic.AtomicBoolean

@OptIn(ExperimentalPagingApi::class)
class ImageRemoteMediator(
    private val query: String,
    private val firstLoad: AtomicBoolean = AtomicBoolean(true),
    private val api: ImageSearchApi,
    private val imageDb: ImageDatabase
): RemoteMediator<Int, ImageEntity>() {
    @ExperimentalPagingApi
    override suspend fun load(loadType: LoadType, state: PagingState<Int, ImageEntity>): MediatorResult {
        val imageDb = imageDb
        val imageDao = imageDb.imageDao
        if(firstLoad.getAndSet(false)) {
            imageDao.clearAll()
        }
        val loadKey = when(loadType) {
            LoadType.REFRESH -> {
                1
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if(lastItem == null) {
                    1
                } else {
                    lastItem.position / state.config.pageSize + 1
                }
            }
        }

        return try {
            val imageDtos = api.search(query = query, pageNumber = loadKey).imagesResults
            MediatorResult.Success(imageDtos.isEmpty())
            imageDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    imageDao.clearAll()
                }
                val imageEntities = imageDtos.map { it.toImageEntity() }
                imageDao.upsertAll(imageEntities)
            }

            MediatorResult.Success(imageDtos.isEmpty())
        } catch (t: Throwable) {
            MediatorResult.Error(t)
        }
    }
}