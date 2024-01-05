package com.olegaches.imagefinder.domain.repository

import androidx.paging.PagingData
import com.olegaches.imagefinder.domain.enums.Country
import com.olegaches.imagefinder.domain.enums.Language
import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.domain.model.SearchFilter
import com.olegaches.imagefinder.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ImageSearchRepository {
    fun getSuggestions(query: String): Flow<Resource<List<String>>>

    fun searchImages(
        query: String,
        country: Country?,
        language: Language?,
        filter: SearchFilter?
    ): Flow<PagingData<Image>>
}