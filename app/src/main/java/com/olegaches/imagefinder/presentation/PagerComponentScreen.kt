package com.olegaches.imagefinder.presentation

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.Transition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.olegaches.imagefinder.R
import com.olegaches.imagefinder.util.observeAsEvents
import com.skydoves.orbital.Orbital
import com.skydoves.orbital.animateSharedElementTransition
import com.skydoves.orbital.rememberContentWithOrbitalScope
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerComponentScreen(pagerComponent: IPagerComponent) {
    val list = pagerComponent.list.collectAsLazyPagingItems()
    val handleEvent = pagerComponent::handleEvent
    val pagerState = rememberPagerState(initialPage = pagerComponent.index) { list.itemCount }
    var animatableImage by remember { mutableStateOf<String?>(null) }
    var imageOffset by remember { mutableStateOf<IntOffset>(IntOffset.Zero) }
    var imageSize by remember { mutableStateOf<DpSize>(DpSize.Zero) }
    var paddingValues by remember { mutableStateOf<PaddingValues>(PaddingValues()) }
    var expandTargetAnimationState by remember { mutableStateOf<Boolean>(false) }
    pagerComponent.singleEvents.observeAsEvents { event ->
        when (event) {
            is PagerSingleEvent.OnAnimate -> {
                val imagePositionalParam = event.imagePositionalParam
                imageSize = imagePositionalParam.size
                animatableImage = list[imagePositionalParam.index]?.uri ?: ""
                expandTargetAnimationState = event.expand
                imageOffset = imagePositionalParam.offset
                paddingValues = imagePositionalParam.paddingValues
            }
            is PagerSingleEvent.OnBackClicked -> {
                handleEvent(PagerEvent.OnScrollTo(pagerState.currentPage))
            }
        }
    }
    var expandCurrentAnimationState by remember { mutableStateOf<Boolean>(false) }
    LaunchedEffect(Unit) {
        delay(200L)
        expandCurrentAnimationState = expandTargetAnimationState
        delay(400L)
        imageSize = DpSize.Zero
    }
    val image = rememberContentWithOrbitalScope {
        val orbitalScope = this
        AsyncImage(
            modifier = if(expandCurrentAnimationState) {
                Modifier.fillMaxSize()
            } else {
                Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                    )
                    .offset { imageOffset }
                    .size(
                        imageSize,
                    )
            }
                .animateSharedElementTransition(
                    orbitalScope,
                    SpringSpec(stiffness = 1200f),
                    SpringSpec(stiffness = 1200f)
                )
            ,
            model = animatableImage,
            contentScale = ContentScale.Fit,
            contentDescription = null,
        )
    }
    if(imageSize != DpSize.Zero) {
        Orbital(modifier = Modifier.fillMaxSize()) {
            image()
        }
    } else if(animatableImage != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize(),
                state = pagerState
            ) { index ->
                val imageItem = list[index]
                Orbital(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    AsyncImage(
                        model = imageItem?.uri ?: R.drawable.cocktail_placeholder,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}
