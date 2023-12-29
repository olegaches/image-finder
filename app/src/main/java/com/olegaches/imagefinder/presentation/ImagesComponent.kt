package com.olegaches.imagefinder.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class ImagesComponent(
    @Assisted componentContext: ComponentContext,
    @Assisted onImageClicked: (Int) -> Unit,
    @Assisted animateImage: (ImagePositionalParam) -> Unit,
    imageListComponentFactory: (
        ComponentContext,
        (Int) -> Unit,
        (ImagePositionalParam) -> Unit,
    ) -> ImageListComponent,
    topBarComponentFactory: (
        ComponentContext,
        (String) -> Unit
    ) -> TopBarComponent
): ComponentContext by componentContext, IImagesComponent {
    override val imagesListComponent: IImageListComponent = imageListComponentFactory(
        childContext(key = "imagesListComponent"),
        onImageClicked,
        animateImage
    )

    override val topBarComponent: ITopBarComponent = topBarComponentFactory(
        childContext(key = "topBarComponent"),
        { query -> imagesListComponent.handleEvent(ImagesListEvent.SearchImages(query)) }
    )
}