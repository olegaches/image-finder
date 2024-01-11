package com.olegaches.imagefinder.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.domain.model.SearchFilter
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class ImagesComponent(
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
    ) -> ImageListComponent,
    topBarComponentFactory: (
        ComponentContext,
        (String) -> Unit,
        () -> Unit,
    ) -> ImagesTopBarComponent
): ComponentContext by componentContext, IImagesComponent {

    override val imagesListComponent: IImageListComponent = imageListComponentFactory(
        childContext(key = "imagesListComponent"),
        onImageClicked,
        animateImage,
        changeFilterState
    )

    override val topBarComponent: IImagesTopBarComponent = topBarComponentFactory(
        childContext(key = "topBarComponent"),
        { query -> imagesListComponent.handleEvent(ImagesListEvent.SearchImages(query, null)) },
        navigateToFilter,
    )
}