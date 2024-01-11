package com.olegaches.imagefinder.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import rememberDeclarativeModalBottomSheetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesRootComponentScreen(component: IImagesRootComponent) {
    val pagerChild = component.pagerSlot.subscribeAsState().value.child?.instance
    ImagesComponentScreen(imagesComponent = component.imagesComponent)
    if(pagerChild != null) {
        when(pagerChild) {
            is IImagesRootComponent.PagerChild.ImageDetail -> {
                ImageDetailComponentScreen(imageDetailComponent = pagerChild.component)
            }
        }
    }
    val sheetState = rememberDeclarativeModalBottomSheetState(
        slot = component.sheetSlot,
        onDismiss = component::dismissSheet
    ) {
        when(val child = it.instance) {
            is IImagesRootComponent.SheetChild.Filter -> {
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