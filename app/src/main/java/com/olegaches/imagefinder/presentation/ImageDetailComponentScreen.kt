package com.olegaches.imagefinder.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.olegaches.imagefinder.ui.theme.ImageFinderTheme

@Composable
fun ImageDetailComponentScreen(imageDetailComponent: IImageDetailComponent) {
    ImageFinderTheme(
        darkTheme = true
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                DetailTopBar(imageDetailComponent.detailTopBarComponent)
            }
        ) { paddingValues ->
            PagerComponentScreen(pagerComponent = imageDetailComponent.pagerComponent)
        }
    }
}