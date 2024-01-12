package com.olegaches.imagefinder.presentation.image_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemInfo
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import com.olegaches.imagefinder.R
import com.olegaches.imagefinder.domain.model.Image
import com.olegaches.imagefinder.presentation.util.ImagePositionalParam
import com.olegaches.imagefinder.presentation.composables.ErrorLabel
import com.olegaches.imagefinder.util.handleHttpCallException
import com.olegaches.imagefinder.util.observeAsEvents
import com.olegaches.imagefinder.util.toDpSize


@Composable
fun ImagesList(imagesListComponent: ImageListComponent, paddingValues: PaddingValues) {
    val list = imagesListComponent.listState.collectAsLazyPagingItems()
    val gridState = rememberLazyStaggeredGridState()
    val handleEvent = imagesListComponent::handleEvent
    val density = LocalDensity.current
    imagesListComponent.singleEvents.observeAsEvents { event ->
        when(event) {
            is ImageListSingleEvent.ScrollToImage -> {
                val index = event.index
                val layoutInfo = gridState.layoutInfo
                val visibleItemsInfo = layoutInfo.visibleItemsInfo
                val firstVisibleItem = visibleItemsInfo.first()
                val lastVisibleItem = visibleItemsInfo.last()
                val foundElement: LazyStaggeredGridItemInfo = if(index > lastVisibleItem.index) {
                    gridState.scrollToItem(index)
                    val tempElement = gridState.layoutInfo.visibleItemsInfo.first { it.index == index }
                    gridState.scrollToItem(
                        index = index,
                        scrollOffset = - (layoutInfo.viewportSize.height - tempElement.size.height)
                    )
                    gridState.layoutInfo.visibleItemsInfo.first { it.index == index }
                } else if (index < firstVisibleItem.index) {
                    gridState.scrollToItem(index = index)
                    gridState.layoutInfo.visibleItemsInfo.first { it.index == index }
                } else {
                    var element = visibleItemsInfo.firstOrNull { it.index == index }
                    if(element == null) {
                        if(index > visibleItemsInfo[1].index) {
                            gridState.scrollToItem(index)
                            val tempElement = gridState.layoutInfo.visibleItemsInfo.first { it.index == index }
                            gridState.scrollToItem(
                                index = index,
                                scrollOffset = - (layoutInfo.viewportSize.height - tempElement.size.height)
                            )
                        } else {
                            gridState.scrollToItem(index)
                        }
                        gridState.layoutInfo.visibleItemsInfo.first { it.index == index }
                    } else {
                        val viewportHeight = layoutInfo.viewportSize.height
                        val elementHeight = element.size.height
                        val elementOffsetY = element.offset.y
                        if(elementOffsetY + elementHeight > viewportHeight) {
                            gridState.scrollToItem(index, - (viewportHeight - elementHeight))
                            element = gridState.layoutInfo.visibleItemsInfo.first { it.index == index }
                        } else if(elementOffsetY < 0) {
                            gridState.scrollToItem(index)
                            element = gridState.layoutInfo.visibleItemsInfo.first { it.index == index }
                        }
                        element
                    }
                }
                handleEvent(
                    ImagesListEvent.OnAnimateImage(
                        ImagePositionalParam(
                            size = foundElement.size.toDpSize(density),
                            offset = foundElement.offset,
                            index = index,
                            paddingValues = paddingValues
                        )
                    )
                )
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        val loadState = list.loadState
        when(val refreshState = loadState.refresh) {
            is LoadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is LoadState.Error -> {
                ErrorLabel(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    text = handleHttpCallException(refreshState.error).asString(),
                    onRetry = list::retry
                )
            }
            is LoadState.NotLoading -> {
                LazyVerticalStaggeredGrid(
                    modifier = Modifier.fillMaxSize(),
                    state = gridState,
                    columns = StaggeredGridCells.Adaptive(160.dp)
                ) {
                    items(
                        count = list.itemCount,
                        key = list.itemKey { it.id }
                    ) { index: Int ->
                        val imageItem = list[index]
                        if (imageItem != null) {
                            ListItem(
                                imageItem = imageItem,
                                onClick = remember { {
                                    handleEvent(
                                        ImagesListEvent.OnImageClicked(
                                            index = index,
                                            image = imageItem
                                        )
                                    )
                                } }
                            )
                        }
                    }
                    when(val appendState = loadState.append) {
                        is LoadState.Loading -> {
                            item(
                                span = StaggeredGridItemSpan.FullLine,
                            ) {
                                Box(Modifier.fillMaxWidth()) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .align(Alignment.Center),
                                        strokeWidth = 3.dp
                                    )
                                }
                            }
                        }
                        is LoadState.Error -> {
                            item(
                                span = StaggeredGridItemSpan.FullLine,
                            ) {
                                ErrorLabel(
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                                    text = handleHttpCallException(appendState.error).asString(),
                                    onRetry = list::retry
                                )
                            }
                        }
                        is LoadState.NotLoading -> Unit
                    }
                }
            }
        }
    }
}

@Composable
private fun ListItem(
    imageItem: Image,
    onClick: () -> Unit
) {
    val placeHolderRes = remember { R.drawable.cocktail_placeholder }
    val contentScale = remember { ContentScale.FillBounds }
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(
                imageItem.width.toFloat() / imageItem.height,
                false
            )
            .background(Color.White)
            .clickable(onClick = onClick),
        painter = rememberAsyncImagePainter(
            model = imageItem.thumbnail,
            placeholder = painterResource(id = placeHolderRes),
            error = painterResource(id = placeHolderRes),
            contentScale = contentScale
        ),
        contentDescription = null,
        contentScale = contentScale
    )
}