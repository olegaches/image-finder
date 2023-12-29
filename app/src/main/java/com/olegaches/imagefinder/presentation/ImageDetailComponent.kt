package com.olegaches.imagefinder.presentation

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.backhandler.BackCallback
import com.olegaches.imagefinder.domain.model.Image
import kotlinx.coroutines.flow.StateFlow
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class ImageDetailComponent(
    @Assisted
    componentContext: ComponentContext,
    @Assisted
    private val navigateBack: () -> Unit,
    @Assisted
    index: Int,
    @Assisted
    val list: StateFlow<PagingData<Image>>,
    @Assisted
    scrollToImage: (Int) -> Unit,
    pagerComponentFactory: (
        ComponentContext,
        Int,
        StateFlow<PagingData<Image>>,
        scrollToIndex: (Int) -> Unit
    ) -> PagerComponent,
): ComponentContext by componentContext, IImageDetailComponent {

    override val detailTopBarComponent = DetailTopBarComponent(
        childContext(key = "detailTopBarComponent"),
        navigateBack
    )

    override val pagerComponent = pagerComponentFactory(
        childContext(key = "pagerComponent"),
        index,
        list,
        scrollToImage
    )

    private val backCallback = BackCallback {
        detailTopBarComponent.changeVisibility(false)
        pagerComponent.handleEvent(PagerEvent.OnBackClicked)
    }

    init {
        backHandler.register(backCallback)
    }
}