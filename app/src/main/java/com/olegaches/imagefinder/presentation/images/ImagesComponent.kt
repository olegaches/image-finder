package com.olegaches.imagefinder.presentation.images

import com.olegaches.imagefinder.presentation.image_list.ImageListComponent
import com.olegaches.imagefinder.presentation.images_topbar.ImagesTopBarComponent

interface ImagesComponent {
    val topBarComponent: ImagesTopBarComponent
    val imagesListComponent: ImageListComponent
}
