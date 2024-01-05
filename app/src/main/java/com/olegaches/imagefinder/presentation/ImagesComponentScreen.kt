package com.olegaches.imagefinder.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ImagesComponentScreen(imagesComponent: IImagesComponent) {
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