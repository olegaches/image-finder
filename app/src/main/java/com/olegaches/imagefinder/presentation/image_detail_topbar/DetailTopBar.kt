package com.olegaches.imagefinder.presentation.image_detail_topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(detailTopBarComponent: DetailTopBarComponent) {
    val visible = detailTopBarComponent.state.collectAsStateWithLifecycle().value.visible
    val transitionState = remember {
        MutableTransitionState(false)
    }
    LaunchedEffect(visible) {
        transitionState.targetState = visible
    }
    AnimatedVisibility(
        visibleState = transitionState,
        enter = fadeIn(tween(700, 100)),
        exit = fadeOut(tween(500))
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
            ),
            title = {},
            navigationIcon = {
                IconButton(onClick = detailTopBarComponent::onBackIconClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
    }
}