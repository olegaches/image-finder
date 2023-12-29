package com.olegaches.imagefinder.domain.use_case

import com.olegaches.imagefinder.domain.repository.ImageSearchRepository
import com.olegaches.imagefinder.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import me.tatarka.inject.annotations.Inject

@Inject
class GetSuggestionsUseCase(
    private val repository: ImageSearchRepository
) {
    operator fun invoke(query: String): Flow<Resource<List<String>>> {
        return repository.getSuggestions(query).flowOn(Dispatchers.IO)
    }
}