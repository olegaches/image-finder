package com.olegaches.imagefinder.presentation

import androidx.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.util.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class PagerComponent(
    @Assisted
    componentContext: ComponentContext,
    @Assisted
    override val index: Int,
    @Assisted
    override val list: StateFlow<PagingData<Image>>,
    @Assisted
    private val scrollToImage: (Int) -> Unit,
    @Assisted
    private val onCurrentImageChanged: (Image?) -> Unit,
    @Assisted
    private val navigateBack: () -> Unit
): IPagerComponent, ComponentContext by componentContext {

    private val _singleEvents = Channel<PagerSingleEvent>()
    override val singleEvents = _singleEvents.receiveAsFlow()

    private val expanded = MutableStateFlow(false)

    private val componentScope = coroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    init {
        scrollToImage(index)
    }

    override fun handleEvent(event: PagerEvent) {
        componentScope.launch {
            when(event) {
                is PagerEvent.OnAnimateImage -> {
                    _singleEvents.send(PagerSingleEvent.OnAnimate(!expanded.value, event.imagePositionalParam))
                    expanded.update { !it }
                }

                PagerEvent.OnBackClicked -> {
                    _singleEvents.send(PagerSingleEvent.OnBackClicked)
                }

                is PagerEvent.OnScrollTo -> {
                    scrollToImage(event.index)
                }

                PagerEvent.NavigateBack -> {
                    navigateBack()
                }
                is PagerEvent.OnCurrentImageChanged -> {
                    onCurrentImageChanged(event.image)
                }
            }
        }
    }
}