package com.olegaches.imagefinder.presentation.image_detail

import androidx.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.backhandler.BackCallback
import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.presentation.image_detail_topbar.DetailTopBarComponentImpl
import com.olegaches.imagefinder.presentation.image_detail_bottombar.ImageDetailBottomBarComponent
import com.olegaches.imagefinder.presentation.image_detail_bottombar.ImageDetailBottomBarComponentImpl
import com.olegaches.imagefinder.presentation.pager.PagerComponentImpl
import com.olegaches.imagefinder.presentation.pager.PagerEvent
import kotlinx.coroutines.flow.StateFlow

class ImageDetailComponentImpl(
    componentContext: ComponentContext,
    navigateBack: () -> Unit,
    index: Int,
    image: Image,
    val list: StateFlow<PagingData<Image>>,
    scrollToImage: (Int) -> Unit,
    navigateToImageSource: (String) -> Unit,
): ComponentContext by componentContext, ImageDetailComponent {

    override val detailTopBarComponent = DetailTopBarComponentImpl(
        childContext(key = "detailTopBarComponent"),
        {
            pagerComponent.handleEvent(PagerEvent.OnBackClicked)
            detailBottomBarComponent.changeVisibility(false)
        }
    )

    override val detailBottomBarComponent: ImageDetailBottomBarComponent =
        ImageDetailBottomBarComponentImpl(
            childContext(key = "detailBottomBarComponent"),
            image,
            navigateToImageSource
        )

    override val pagerComponent = PagerComponentImpl(
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