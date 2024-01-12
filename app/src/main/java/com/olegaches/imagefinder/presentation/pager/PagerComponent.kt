package com.olegaches.imagefinder.presentation.pager

import androidx.paging.PagingData
import com.olegaches.imagefinder.domain.model.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface PagerComponent {
    val list: StateFlow<PagingData<Image>>

    val index: Int

    val singleEvents: Flow<PagerSingleEvent>

    fun handleEvent(event: PagerEvent)
}
