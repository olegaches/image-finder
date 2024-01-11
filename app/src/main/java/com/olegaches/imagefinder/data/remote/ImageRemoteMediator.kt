package com.olegaches.imagefinder.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.olegaches.imagefinder.data.local.ImageDatabase
import com.olegaches.imagefinder.data.local.entity.ImageEntity
import com.olegaches.imagefinder.domain.enums.Country
import com.olegaches.imagefinder.domain.enums.Language
import com.olegaches.imagefinder.domain.model.SearchFilter
import com.olegaches.imagefinder.toImageEntity
import java.util.concurrent.atomic.AtomicBoolean

@OptIn(ExperimentalPagingApi::class)
class ImageRemoteMediator(
    private val query: String,
    private val language: Language?,
    private val country: Country?,
    private val filter: SearchFilter?,
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
                0
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if(lastItem == null) {
                    return MediatorResult.Success(false)
                } else {
                    (lastItem.id + 1) / state.config.pageSize
                }
            }
        }

        return try {
            val searchResult = api.search(
                query = query,
                pageNumber = loadKey,
                language = language,
                country = country,
                filter = filter,
            )
            val endOfPagination = searchResult.pagination.next == null || searchResult.imagesResults.isNullOrEmpty()
            imageDb.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    imageDao.clearAll()
                }
                val imageEntities = searchResult.imagesResults?.map { it.toImageEntity() }
                if (imageEntities != null) {
                    imageDao.upsertAll(imageEntities)
                }
            }
            MediatorResult.Success(endOfPagination)
        } catch (t: Throwable) {
            MediatorResult.Error(t)
        }
    }
}