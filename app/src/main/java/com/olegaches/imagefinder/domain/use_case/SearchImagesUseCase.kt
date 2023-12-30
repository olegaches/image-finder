package com.olegaches.imagefinder.domain.use_case

import androidx.paging.PagingData
import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.domain.repository.ImageSearchRepository
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class SearchImagesUseCase(
    private val repository: ImageSearchRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Image>> {
        return repository.searchImages(query)
    }
}