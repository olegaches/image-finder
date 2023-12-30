package com.olegaches.imagefinder.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemInfo
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.olegaches.imagefinder.R
import com.olegaches.imagefinder.util.observeAsEvents
import com.olegaches.imagefinder.util.toDpSize
import java.util.UUID


@Composable
fun ImagesList(imagesListComponent: IImageListComponent, paddingValues: PaddingValues) {
    val list = imagesListComponent.listState.collectAsLazyPagingItems()
    val gridState = rememberLazyStaggeredGridState()
    val handleEvent = imagesListComponent::handleEvent
    val density = LocalDensity.current
    imagesListComponent.singleEvents.observeAsEvents { event ->
        when(event) {
            is ImageListSingleEvent.OnPagerDismiss -> {
                val index = event.index
                val layoutInfo = gridState.layoutInfo
                val visibleItemsInfo = layoutInfo.visibleItemsInfo
                val firstVisibleItem = visibleItemsInfo.first()
                val lastVisibleItem = visibleItemsInfo.last()
                val foundElement: LazyStaggeredGridItemInfo
                if(index > lastVisibleItem.index) {
                    gridState.scrollToItem(index)
                    foundElement = gridState.layoutInfo.visibleItemsInfo.first { it.index == index }
                    gridState.scrollToItem(
                        index = index,
                        scrollOffset = layoutInfo.viewportSize.height - foundElement.size.height
                    )
                } else if (index < firstVisibleItem.index) {
                    gridState.scrollToItem(index = index)
                    foundElement = gridState.layoutInfo.visibleItemsInfo.first { it.index == index }
                } else {
                    foundElement = gridState.layoutInfo.visibleItemsInfo.first { it.index == index }
                }
                handleEvent(ImagesListEvent.OnAnimateImage(ImagePositionalParam(
                    size = foundElement.size.toDpSize(density),
                    offset = foundElement.offset,
                    index = index,
                    paddingValues = paddingValues
                )))
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
                    modifier = Modifier.fillMaxSize(),
                    text = refreshState.error.message + " 11111111111"
                ) {
                    TODO()
                }
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
                        if(imageItem != null) {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(imageItem.width.toFloat() / imageItem.height, false)
                                    .clickable {
                                        handleEvent(
                                            ImagesListEvent.OnImageClicked(
                                                index = index
                                            )
                                        )
                                    },
                                model = imageItem.uri,
                                placeholder = painterResource(id = R.drawable.cocktail_placeholder),
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds
                            )
                        }
                    }
                    when(val appendState = loadState.append) {
                        is LoadState.Loading -> {
                            item(
                                span = StaggeredGridItemSpan.FullLine,
                               // key = list.itemKey { it.id }
                            ) {
                                Box(Modifier.fillMaxWidth()) {
                                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                }
                            }
                        }
                        is LoadState.Error -> {
                            item(
                                span = StaggeredGridItemSpan.FullLine,
                                //key = list.itemKey { it.id }
                            ) {
                                ErrorLabel(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = appendState.error.message + " 2222222222222"
                                ) {
                                    TODO()
                                }
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
fun ErrorLabel(
    modifier: Modifier,
    text: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onRetry) {
            Text(
                text = stringResource(R.string.retry),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

data class Fuck(
    val name: String = UUID.randomUUID().toString(),
    val poster: String
)

class MockUtils {
    companion object {
        fun getMockPosters(): List<Fuck> {
            return listOf(
                Fuck(poster ="https://img.freepik.com/free-photo/painting-mountain-lake-with-mountain-background_188544-9126.jpg"),
                Fuck(poster ="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQgGPPZkESatGZ4Ansdex3I1bhvn0QeTrSYda7CCLqoRH6GAnbrQGV8IcQxmAnX62HuB8&usqp=CAU"),
                Fuck(poster ="https://pixlr.com/images/index/ai-image-generator-two.webp"),
                Fuck(poster ="https://media.istockphoto.com/id/508214067/photo/big-wooden-boats-in-water-with-cloudy-sky-and-sunbeams.jpg?s=612x612&w=0&k=20&c=oKkKVc7X035-CMtJX_yTC-K5JOFvXuxfWh1YMC9ZYgs="),
                Fuck(poster ="https://thumbs.dreamstime.com/b/web-182027161.jpg"),
                Fuck(poster ="https://img.freepik.com/free-photo/painting-mountain-lake-with-mountain-background_188544-9126.jpg"),
                Fuck(poster ="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQgGPPZkESatGZ4Ansdex3I1bhvn0QeTrSYda7CCLqoRH6GAnbrQGV8IcQxmAnX62HuB8&usqp=CAU"),
                Fuck(poster ="https://pixlr.com/images/index/ai-image-generator-two.webp"),
                Fuck(poster ="https://media.istockphoto.com/id/508214067/photo/big-wooden-boats-in-water-with-cloudy-sky-and-sunbeams.jpg?s=612x612&w=0&k=20&c=oKkKVc7X035-CMtJX_yTC-K5JOFvXuxfWh1YMC9ZYgs="),
                Fuck(poster ="https://thumbs.dreamstime.com/b/web-182027161.jpg"),
                Fuck(poster ="https://img.freepik.com/free-photo/painting-mountain-lake-with-mountain-background_188544-9126.jpg"),
                Fuck(poster ="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQgGPPZkESatGZ4Ansdex3I1bhvn0QeTrSYda7CCLqoRH6GAnbrQGV8IcQxmAnX62HuB8&usqp=CAU"),
                Fuck(poster ="https://pixlr.com/images/index/ai-image-generator-two.webp"),
                Fuck(poster ="https://media.istockphoto.com/id/508214067/photo/big-wooden-boats-in-water-with-cloudy-sky-and-sunbeams.jpg?s=612x612&w=0&k=20&c=oKkKVc7X035-CMtJX_yTC-K5JOFvXuxfWh1YMC9ZYgs="),
                Fuck(poster ="https://thumbs.dreamstime.com/b/web-182027161.jpg"),
                Fuck(poster ="https://img.freepik.com/free-photo/painting-mountain-lake-with-mountain-background_188544-9126.jpg"),
                Fuck(poster ="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQgGPPZkESatGZ4Ansdex3I1bhvn0QeTrSYda7CCLqoRH6GAnbrQGV8IcQxmAnX62HuB8&usqp=CAU"),
                Fuck(poster ="https://pixlr.com/images/index/ai-image-generator-two.webp"),
                Fuck(poster ="https://media.istockphoto.com/id/508214067/photo/big-wooden-boats-in-water-with-cloudy-sky-and-sunbeams.jpg?s=612x612&w=0&k=20&c=oKkKVc7X035-CMtJX_yTC-K5JOFvXuxfWh1YMC9ZYgs="),
                Fuck(poster ="https://thumbs.dreamstime.com/b/web-182027161.jpg"),
                Fuck(poster ="https://img.freepik.com/free-photo/painting-mountain-lake-with-mountain-background_188544-9126.jpg"),
                Fuck(poster ="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQgGPPZkESatGZ4Ansdex3I1bhvn0QeTrSYda7CCLqoRH6GAnbrQGV8IcQxmAnX62HuB8&usqp=CAU"),
                Fuck(poster ="https://pixlr.com/images/index/ai-image-generator-two.webp"),
                Fuck(poster ="https://media.istockphoto.com/id/508214067/photo/big-wooden-boats-in-water-with-cloudy-sky-and-sunbeams.jpg?s=612x612&w=0&k=20&c=oKkKVc7X035-CMtJX_yTC-K5JOFvXuxfWh1YMC9ZYgs="),
                Fuck(poster ="https://thumbs.dreamstime.com/b/web-182027161.jpg"),
                Fuck(poster ="https://img.freepik.com/free-photo/painting-mountain-lake-with-mountain-background_188544-9126.jpg"),
                Fuck(poster ="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQgGPPZkESatGZ4Ansdex3I1bhvn0QeTrSYda7CCLqoRH6GAnbrQGV8IcQxmAnX62HuB8&usqp=CAU"),
                Fuck(poster ="https://pixlr.com/images/index/ai-image-generator-two.webp"),
                Fuck(poster ="https://media.istockphoto.com/id/508214067/photo/big-wooden-boats-in-water-with-cloudy-sky-and-sunbeams.jpg?s=612x612&w=0&k=20&c=oKkKVc7X035-CMtJX_yTC-K5JOFvXuxfWh1YMC9ZYgs="),
                Fuck(poster ="https://thumbs.dreamstime.com/b/web-182027161.jpg"),
                Fuck(poster ="https://img.freepik.com/free-photo/painting-mountain-lake-with-mountain-background_188544-9126.jpg"),
                Fuck(poster ="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQgGPPZkESatGZ4Ansdex3I1bhvn0QeTrSYda7CCLqoRH6GAnbrQGV8IcQxmAnX62HuB8&usqp=CAU"),
                Fuck(poster ="https://pixlr.com/images/index/ai-image-generator-two.webp"),
                Fuck(poster ="https://media.istockphoto.com/id/508214067/photo/big-wooden-boats-in-water-with-cloudy-sky-and-sunbeams.jpg?s=612x612&w=0&k=20&c=oKkKVc7X035-CMtJX_yTC-K5JOFvXuxfWh1YMC9ZYgs="),
                Fuck(poster ="https://thumbs.dreamstime.com/b/web-182027161.jpg"),
                Fuck(poster ="https://img.freepik.com/free-photo/painting-mountain-lake-with-mountain-background_188544-9126.jpg"),
                Fuck(poster ="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQgGPPZkESatGZ4Ansdex3I1bhvn0QeTrSYda7CCLqoRH6GAnbrQGV8IcQxmAnX62HuB8&usqp=CAU"),
                Fuck(poster ="https://pixlr.com/images/index/ai-image-generator-two.webp"),
                Fuck(poster ="https://media.istockphoto.com/id/508214067/photo/big-wooden-boats-in-water-with-cloudy-sky-and-sunbeams.jpg?s=612x612&w=0&k=20&c=oKkKVc7X035-CMtJX_yTC-K5JOFvXuxfWh1YMC9ZYgs="),
                Fuck(poster ="https://thumbs.dreamstime.com/b/web-182027161.jpg"),
            )
        }
    }
}