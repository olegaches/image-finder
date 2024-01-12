package com.olegaches.imagefinder.presentation.images

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.domain.model.SearchFilter
import com.olegaches.imagefinder.presentation.image_list.ImageListComponent
import com.olegaches.imagefinder.presentation.images_topbar.ImagesTopBarComponent
import com.olegaches.imagefinder.presentation.image_list.ImageListComponentImpl
import com.olegaches.imagefinder.presentation.util.ImagePositionalParam
import com.olegaches.imagefinder.presentation.image_list.ImagesListEvent
import com.olegaches.imagefinder.presentation.images_topbar.ImagesTopBarComponentImpl
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class ImagesComponentImpl(
    @Assisted componentContext: ComponentContext,
    @Assisted onImageClicked: (Int, Image) -> Unit,
    @Assisted animateImage: (ImagePositionalParam) -> Unit,
    @Assisted navigateToFilter: () -> Unit,
    @Assisted changeFilterState: (SearchFilter?) -> Unit,
    imageListComponentFactory: (
        ComponentContext,
        (Int, Image) -> Unit,
        (ImagePositionalParam) -> Unit,
        (SearchFilter?) -> Unit,
    ) -> ImageListComponentImpl,
    topBarComponentFactory: (
        ComponentContext,
        (String) -> Unit,
        () -> Unit,
    ) -> ImagesTopBarComponentImpl
): ComponentContext by componentContext, ImagesComponent {

    override val imagesListComponent: ImageListComponent = imageListComponentFactory(
        childContext(key = "imagesListComponent"),
        onImageClicked,
        animateImage,
        changeFilterState
    )

    override val topBarComponent: ImagesTopBarComponent = topBarComponentFactory(
        childContext(key = "topBarComponent"),
        { query -> imagesListComponent.handleEvent(ImagesListEvent.SearchImages(query, null)) },
        navigateToFilter,
    )
}