package com.olegaches.imagefinder.presentation

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arkivanov.decompose.ComponentContext
import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.domain.use_case.SearchImagesUseCase
import com.olegaches.imagefinder.util.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class ImageListComponent(
    @Assisted
    componentContext: ComponentContext,
    @Assisted
    private val onImageClicked: (Int, Image) -> Unit,
    @Assisted
    private val animateImage: (ImagePositionalParam) -> Unit,
    private val searchImagesUseCase: SearchImagesUseCase,
): IImageListComponent, ComponentContext by componentContext {
    private val componentScope = coroutineScope(Dispatchers.Main.immediate + SupervisorJob())
    private val _listState: MutableStateFlow<PagingData<Image>> = MutableStateFlow(value = PagingData.empty(
        LoadStates(
            refresh = LoadState.NotLoading(true),
            append = LoadState.NotLoading(true),
            prepend = LoadState.NotLoading(true)
        )
    ))
    override val listState: MutableStateFlow<PagingData<Image>> get() = _listState

    private val _singleEvents = Channel<ImageListSingleEvent>()
    override val singleEvents = _singleEvents.receiveAsFlow()

    override fun handleEvent(event: ImagesListEvent) {
        componentScope.launch {
            when(event) {
                is ImagesListEvent.SearchImages -> {
                    searchImagesUseCase(
                        query = event.query,
                        language = null,
                        country = null,
                        filter = null,
                    )
                        .distinctUntilChanged()
                        .cachedIn(componentScope)
                        .collect { pagingData ->
                            _listState.value = pagingData
                        }
                }
                is ImagesListEvent.OnImageClicked -> {
                    onImageClicked(event.index, event.image)
                }
                is ImagesListEvent.OnAnimateImage -> {
                    animateImage(event.imagePosParams)
                }
                is ImagesListEvent.OnScrollToImage -> {
                    _singleEvents.send(ImageListSingleEvent.OnPagerDismiss(event.index))
                }
            }
        }
    }
}