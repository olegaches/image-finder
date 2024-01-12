package com.olegaches.imagefinder.presentation.image_detail_bottombar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.olegaches.imagefinder.R

@Composable
fun ImageDetailBottomBar(imageDetailBottomBarComponent: ImageDetailBottomBarComponent) {
    val visible = imageDetailBottomBarComponent.visible.collectAsStateWithLifecycle().value
    val image = imageDetailBottomBarComponent.image.collectAsStateWithLifecycle().value
    val visibilityState = remember { MutableTransitionState(false) }

    LaunchedEffect(visible) {
        visibilityState.targetState = visible
    }

    AnimatedVisibility(
        visibleState = visibilityState,
        enter = fadeIn(tween(700, 100)),
        exit = fadeOut(tween(500))
    ) {
        BottomAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
        ) {
            AnimatedContent(
                targetState = image,
                label = "",
                transitionSpec = {
                    ContentTransform(fadeIn(), fadeOut())
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (it != null) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = it.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(Modifier.width(12.dp))
                        OutlinedButton(
                            modifier = Modifier
                                .height(30.dp)
                                .weight(1f),
                            onClick = imageDetailBottomBarComponent::onSourceClicked,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.go_to_source),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}