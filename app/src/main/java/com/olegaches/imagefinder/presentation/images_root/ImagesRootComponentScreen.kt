package com.olegaches.imagefinder.presentation.images_root

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.olegaches.imagefinder.presentation.image_filter.FilterComponentScreen
import com.olegaches.imagefinder.presentation.image_detail.ImageDetailScreen
import com.olegaches.imagefinder.presentation.images.ImagesComponentScreen
import rememberDeclarativeModalBottomSheetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesRootComponentScreen(component: ImagesRootComponent) {
    val pagerChild = component.pagerSlot.subscribeAsState().value.child?.instance
    ImagesComponentScreen(imagesComponent = component.imagesComponent)
    if(pagerChild != null) {
        when(pagerChild) {
            is ImagesRootComponent.PagerChild.ImageDetail -> {
                ImageDetailScreen(imageDetailComponent = pagerChild.component)
            }
        }
    }
    val sheetState = rememberDeclarativeModalBottomSheetState(
        slot = component.sheetSlot,
        onDismiss = component::dismissSheet
    ) {
        when(val child = it.instance) {
            is ImagesRootComponent.SheetChild.Filter -> {
                FilterComponentScreen(filterComponent = child.component)
            }
        }
    }
    if(component.sheetSlot.value.child?.instance != null) {
        ModalBottomSheet(
            sheetState = sheetState.sheetState,
            content = sheetState.sheetContent.value,
            onDismissRequest = {},
            dragHandle = {}
        )
    }
}