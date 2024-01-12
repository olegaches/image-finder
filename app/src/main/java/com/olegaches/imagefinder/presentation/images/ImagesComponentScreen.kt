package com.olegaches.imagefinder.presentation.images

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.olegaches.imagefinder.presentation.image_list.ImagesList
import com.olegaches.imagefinder.presentation.images_topbar.ImagesTopBar

@Composable
fun ImagesComponentScreen(imagesComponent: ImagesComponent) {
    Scaffold(
        topBar = {
            ImagesTopBar(imagesComponent.topBarComponent)
        },
    ) { paddingValues ->
        Box(
           modifier = Modifier
               .padding(paddingValues)
               .fillMaxSize(),
        ) {
            ImagesList(
                imagesListComponent = imagesComponent.imagesListComponent,
                paddingValues = paddingValues
            )
        }
    }
}