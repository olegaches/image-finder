package com.olegaches.imagefinder.presentation.pager

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.olegaches.imagefinder.R
import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.util.observeAsEvents
import com.skydoves.orbital.Orbital
import com.skydoves.orbital.animateSharedElementTransition
import com.skydoves.orbital.rememberContentWithOrbitalScope
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerComponentScreen(pagerComponent: PagerComponent) {
    val list = pagerComponent.list.collectAsLazyPagingItems()
    val handleEvent = pagerComponent::handleEvent
    val pagerState = rememberPagerState(initialPage = pagerComponent.index) { list.itemCount }
    var animatableImage by remember { mutableStateOf<Image?>(null) }
    var imageOffset by remember { mutableStateOf<IntOffset>(IntOffset.Zero) }
    var imageSize by remember { mutableStateOf<DpSize>(DpSize.Zero) }
    var paddingValues by remember { mutableStateOf<PaddingValues>(PaddingValues()) }
    var animationFinished by rememberSaveable { mutableStateOf<Boolean>(false) }
    var expandAnimationState by rememberSaveable { mutableStateOf<Boolean>(false) }
    val backgroundColor by animateColorAsState(
        targetValue = if (expandAnimationState) MaterialTheme.colorScheme.background else Color.Transparent,
        animationSpec = tween(durationMillis = 400),
        label = ""
    )
    pagerComponent.singleEvents.observeAsEvents { event ->
        when (event) {
            is PagerSingleEvent.OnAnimate -> {
                animationFinished = false
                val imagePositionalParam = event.imagePositionalParam
                imageSize = imagePositionalParam.size
                animatableImage = list[imagePositionalParam.index]
                imageOffset = imagePositionalParam.offset
                paddingValues = imagePositionalParam.paddingValues
                delay(100L)
                expandAnimationState = event.expand
                delay(400L)
                animatableImage = null
                if(!expandAnimationState) {
                    handleEvent(PagerEvent.NavigateBack)
                }
                animationFinished = true
            }
            is PagerSingleEvent.OnBackClicked -> {
                handleEvent(PagerEvent.OnScrollTo(pagerState.currentPage))
            }
        }
    }

    val settledPage = pagerState.currentPage
    LaunchedEffect(settledPage) {
        handleEvent(PagerEvent.OnCurrentImageChanged(list[settledPage]))
    }

    val image = rememberContentWithOrbitalScope {
        val orbitalScope = this
        AsyncImage(
            modifier = if (expandAnimationState) {
                Modifier
                    .fillMaxSize()
                    .aspectRatio(
                        animatableImage!!.width.toFloat() / animatableImage!!.height,
                        false
                    )
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
                    SpringSpec(stiffness = 400f),
                    SpringSpec(stiffness = 400f)
                )
                .background(Color.White),
            model = animatableImage!!.thumbnail,
            error = painterResource(id = R.drawable.cocktail_placeholder),
            placeholder = painterResource(id = R.drawable.cocktail_placeholder),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .alpha(if (animationFinished) 1f else 0f),
            state = pagerState,
            key = list.itemKey { it.id }
        ) { index ->
            val imageItem = list[index]
            if(imageItem != null) {
                val placeholderPainter = rememberAsyncImagePainter(
                    model = imageItem.thumbnail,
                    placeholder = painterResource(id = R.drawable.cocktail_placeholder),
                    error = painterResource(id = R.drawable.cocktail_placeholder),
                    contentScale = ContentScale.FillBounds
                )
                AsyncImage(
                    model = imageItem.uri,
                    error = placeholderPainter,
                    placeholder = placeholderPainter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(imageItem.width.toFloat() / imageItem.height, false)
                        .background(Color.White),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
        if(animatableImage != null) {
            Orbital(modifier = Modifier.fillMaxSize()) {
                image()
            }
        }
    }
}
