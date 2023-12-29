package com.olegaches.imagefinder.domain.repository

import androidx.paging.PagingData
import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ImageSearchRepository {
    fun searchImages(query: String): Flow<PagingData<Image>>

    fun getSuggestions(query: String): Flow<Resource<List<String>>>
}