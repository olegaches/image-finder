package com.olegaches.imagefinder.presentation

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
    image: Image,
    @Assisted
    val list: StateFlow<PagingData<Image>>,
    @Assisted
    scrollToImage: (Int) -> Unit,
    @Assisted
    navigateToImageSource: (String) -> Unit,
    pagerComponentFactory: (
        ComponentContext,
        Int,
        StateFlow<PagingData<Image>>,
        (Int) -> Unit,
        (Image?) -> Unit,
        () -> Unit
    ) -> PagerComponent,
): ComponentContext by componentContext, IImageDetailComponent {

    override val detailTopBarComponent = DetailTopBarComponent(
        childContext(key = "detailTopBarComponent"),
        {
            pagerComponent.handleEvent(PagerEvent.OnBackClicked)
            detailBottomBarComponent.changeVisibility(false)
        }
    )

    override val detailBottomBarComponent: IImageDetailBottomBarComponent =
        ImageDetailBottomBarComponent(
            childContext(key = "detailBottomBarComponent"),
            image,
            navigateToImageSource
        )

    override val pagerComponent = pagerComponentFactory(
        childContext(key = "pagerComponent"),
        index,
        list,
        scrollToImage,
        detailBottomBarComponent::updateImage,
        navigateBack
    )

    private val backCallback = BackCallback {
        detailTopBarComponent.changeVisibility(false)
        detailBottomBarComponent.changeVisibility(false)
        pagerComponent.handleEvent(PagerEvent.OnBackClicked)
    }

    init {
        backHandler.register(backCallback)
    }
}