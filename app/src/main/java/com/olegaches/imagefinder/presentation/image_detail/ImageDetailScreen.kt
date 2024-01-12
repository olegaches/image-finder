package com.olegaches.imagefinder.presentation.image_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.olegaches.imagefinder.presentation.image_detail_topbar.DetailTopBar
import com.olegaches.imagefinder.presentation.image_detail_bottombar.ImageDetailBottomBar
import com.olegaches.imagefinder.presentation.pager.PagerComponentScreen
import com.olegaches.imagefinder.ui.theme.ImageFinderTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ImageDetailScreen(imageDetailComponent: ImageDetailComponent) {
    ImageFinderTheme(
        darkTheme = true
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                DetailTopBar(imageDetailComponent.detailTopBarComponent)
            },
            bottomBar = {
                ImageDetailBottomBar(imageDetailComponent.detailBottomBarComponent)
            }
        ) {
            PagerComponentScreen(pagerComponent = imageDetailComponent.pagerComponent)
        }
    }
}