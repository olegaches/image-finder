package com.olegaches.imagefinder.presentation.image_filter

import com.arkivanov.decompose.ComponentContext
import com.olegaches.imagefinder.domain.enums.ImageSize
import com.olegaches.imagefinder.domain.enums.ImageType
import com.olegaches.imagefinder.domain.model.SearchFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FilterComponentImpl(
    componentContext: ComponentContext,
    private val applyFilter: (SearchFilter?) -> Unit,
    filter: SearchFilter?
): ComponentContext by componentContext, FilterComponent {
    private val _state = MutableStateFlow(filter?.let { FilterState(it.size, it.type) } ?: FilterState())
    override val state = _state.asStateFlow()

    override fun handleEvent(event: FilterEvent) {
        val stateFlow = _state
        when(event) {
            is FilterEvent.ChangeSize -> {
                stateFlow.update { it.copy(size = event.size) }
            }
            is FilterEvent.ChangeType -> {
                stateFlow.update { it.copy(type = event.type) }
            }

            FilterEvent.ApplyFilter -> {
                val (size, type) = stateFlow.value.let { it.size to it.type }
                val searchFilter = if(size == ImageSize.ANY_SIZE && type == ImageType.ANY_TYPE) {
                    null
                } else {
                    SearchFilter(size = size, type = type)
                }
                applyFilter(searchFilter)
            }
        }
    }
}