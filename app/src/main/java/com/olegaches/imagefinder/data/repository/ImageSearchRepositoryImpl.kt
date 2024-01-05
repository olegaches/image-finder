package com.olegaches.imagefinder.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.olegaches.imagefinder.data.local.ImageDatabase
import com.olegaches.imagefinder.data.remote.ImageRemoteMediator
import com.olegaches.imagefinder.data.remote.ImageSearchApi
import com.olegaches.imagefinder.domain.enums.Language
import com.olegaches.imagefinder.domain.enums.Country
import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.domain.model.SearchFilter
import com.olegaches.imagefinder.domain.repository.ImageSearchRepository
import com.olegaches.imagefinder.toImage
import com.olegaches.imagefinder.domain.util.Resource
import com.olegaches.imagefinder.util.handleHttpCallException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class ImageSearchRepositoryImpl(
    private val imagesSearchApi: ImageSearchApi,
    private val imageDb: ImageDatabase,
): ImageSearchRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun searchImages(
        query: String,
        country: Country?,
        language: Language?,
        filter: SearchFilter?
        ): Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(pageSize = 100, initialLoadSize = 100),
            remoteMediator = ImageRemoteMediator(
                query = query,
                country = country,
                language = language,
                filter = filter,
                api = imagesSearchApi,
                imageDb = imageDb
            ),
            pagingSourceFactory = { imageDb.imageDao.pagingSource() }
        )
            .flow
            .map { pagingData -> pagingData.map { it.toImage() } }
    }

    override fun getSuggestions(query: String): Flow<Resource<List<String>>> {
        return flow {
            emit(safeApiCall {
                val resultList = imagesSearchApi.autocomplete(query = query).suggestions
                resultList
                    .map { it.value }
                .take(10.coerceAtMost(resultList.size))
            })
        }
    }

    private inline fun <T> safeApiCall(apiCall: () -> T): Resource<T> {
        return try {
            val data = apiCall()
            Resource.Success(data = data)
        } catch(throwable: Throwable) {
            Resource.Error(handleHttpCallException(throwable))
        }
    }
}