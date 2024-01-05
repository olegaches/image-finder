package com.olegaches.imagefinder.domain.use_case

import androidx.paging.PagingData
import com.olegaches.imagefinder.domain.enums.Country
import com.olegaches.imagefinder.domain.enums.Language
import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.domain.model.SearchFilter
import com.olegaches.imagefinder.domain.repository.ImageSearchRepository
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class SearchImagesUseCase(
    private val repository: ImageSearchRepository
) {
    operator fun invoke(
        query: String,
        country: Country?,
        language: Language?,
        filter: SearchFilter?
    ): Flow<PagingData<Image>> {
        return repository.searchImages(
            query = query,
            country = country,
            language = language,
            filter = filter
        )
    }
}