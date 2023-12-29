package com.olegaches.imagefinder.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.olegaches.imagefinder.R
import com.olegaches.imagefinder.data.local.ImageDatabase
import com.olegaches.imagefinder.data.remote.ImageRemoteMediator
import com.olegaches.imagefinder.data.remote.ImageSearchApi
import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.domain.repository.ImageSearchRepository
import com.olegaches.imagefinder.toImage
import com.olegaches.imagefinder.domain.util.Resource
import com.olegaches.imagefinder.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import retrofit2.HttpException
import java.io.IOException

@Inject
class ImageSearchRepositoryImpl(
    private val imagesSearchApi: ImageSearchApi,
    private val imageDb: ImageDatabase,
): ImageSearchRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun searchImages(query: String): Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(pageSize = 100),
            remoteMediator = ImageRemoteMediator(
                query = query,
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
            Resource.Error(handleThrowableException(throwable))
        }
    }

    private fun handleThrowableException(throwable: Throwable): UiText {
        return when(throwable) {
            is HttpException -> {
                val localizedMessage = throwable.localizedMessage
                if(localizedMessage.isNullOrEmpty()) {
                    UiText.StringResource(R.string.unknown_exception)
                }
                else {
                    UiText.DynamicString(localizedMessage)
                }
            }
            is IOException -> {
                UiText.StringResource(R.string.io_exception)
            }
            else -> UiText.StringResource(R.string.unknown_exception)
        }
    }
}