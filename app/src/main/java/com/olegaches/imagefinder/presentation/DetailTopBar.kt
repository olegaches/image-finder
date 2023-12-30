package com.olegaches.imagefinder.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(detailTopBarComponent: IDetailTopBarComponent) {
    val state = detailTopBarComponent.state.collectAsStateWithLifecycle().value
    val transitionState = remember {
        MutableTransitionState(false).apply { targetState = state.visible }
    }
    AnimatedVisibility(
        visibleState = transitionState,
        enter = fadeIn(tween(1000)),
        exit = fadeOut(tween(1000))
    ) {
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = detailTopBarComponent::onBackIconClicked) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
    }
}