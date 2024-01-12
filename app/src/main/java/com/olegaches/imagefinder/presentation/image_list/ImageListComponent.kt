package com.olegaches.imagefinder.presentation.image_list

import androidx.paging.PagingData
import com.olegaches.imagefinder.domain.model.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ImageListComponent {
    val listState: StateFlow<PagingData<Image>>

    val singleEvents: Flow<ImageListSingleEvent>

    fun handleEvent(event: ImagesListEvent)
}
