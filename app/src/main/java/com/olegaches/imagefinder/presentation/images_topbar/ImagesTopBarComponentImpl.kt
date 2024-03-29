package com.olegaches.imagefinder.presentation.images_topbar

import com.arkivanov.decompose.ComponentContext
import com.olegaches.imagefinder.domain.use_case.GetSuggestionsUseCase
import com.olegaches.imagefinder.domain.util.Resource
import com.olegaches.imagefinder.util.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class ImagesTopBarComponentImpl(
    @Assisted componentContext: ComponentContext,
    @Assisted private val onSearchClicked: (String) -> Unit,
    @Assisted private val navigateToFilter: () -> Unit,
    private val getSuggestionsUseCase: GetSuggestionsUseCase,
): ComponentContext by componentContext, ImagesTopBarComponent {
    private val componentScope = coroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val _state = MutableStateFlow(TopBarState())
    override val state = _state.asStateFlow()

    private var getSuggestionsJob: Job? = null

    override fun handleEvent(event: ImagesTopBarEvent) {
        componentScope.launch {
            val stateFlow = _state
            when(event) {
                is ImagesTopBarEvent.OnQueryChange -> {
                    val query = event.value
                    stateFlow.update { it.copy(query = query, loading = true, error = null, suggestions = emptyList()) }
                    getSuggestionsJob?.cancel()
                    if(query.isNotBlank()) {
                        getSuggestionsJob = launch {
                            delay(500L)
                            getSuggestionsUseCase(query).collect { result ->
                                when (result) {
                                    is Resource.Success -> {
                                        stateFlow.update { it.copy(suggestions = result.data, loading = false) }
                                    }
                                    is Resource.Error -> {
                                        stateFlow.update { it.copy(error = result.message, loading = false) }
                                    }
                                }
                            }
                        }
                    } else {
                        stateFlow.update { it.copy(loading = false) }
                    }
                }
                is ImagesTopBarEvent.OnSearch -> {
                    val query = event.value
                    if(query.isNotBlank()) {
                        stateFlow.update { it.copy(prevQuery = query, searchBarActive = false, prevSuggestions = it.suggestions) }
                        onSearchClicked(query)
                    }
                }
                is ImagesTopBarEvent.OnBarActiveChange ->  {
                    val searchBarActive = event.value
                    if(searchBarActive) {
                        stateFlow.update { it.copy(searchBarActive = true, suggestions = it.prevSuggestions) }
                    } else {
                        stateFlow.update { it.copy(query = it.prevQuery, searchBarActive = false) }
                    }
                }
                is ImagesTopBarEvent.OnFilterClicked -> navigateToFilter()
            }
        }
    }
}