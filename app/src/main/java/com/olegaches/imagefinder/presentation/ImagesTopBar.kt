package com.olegaches.imagefinder.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.olegaches.imagefinder.R
import com.olegaches.imagefinder.presentation.composables.ErrorLabel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesTopBar(topBarComponent: ITopBarComponent) {
    val state = topBarComponent.state.collectAsStateWithLifecycle().value
    val handleEvent = topBarComponent::handleEvent
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(40.dp),
        query = state.query,
        onQueryChange = { handleEvent(ImagesTopBarEvent.OnQueryChange(it)) },
        onSearch = { handleEvent(ImagesTopBarEvent.OnSearch(it)) },
        active = state.searchBarActive,
        onActiveChange = { handleEvent(ImagesTopBarEvent.OnBarActiveChange(it)) },
        trailingIcon = {
            AnimatedVisibility(
                visible = state.searchBarActive && state.query.isNotEmpty(),
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                IconButton(onClick = { handleEvent(ImagesTopBarEvent.OnQueryChange("")) }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null
                    )
                }
            }
        },
        leadingIcon = {
            AnimatedContent(
                targetState = state.searchBarActive,
                label = "",
                transitionSpec = {
                    ContentTransform(scaleIn(), scaleOut())
                }
            ) { active ->
                if(active) {
                    IconButton(onClick = { handleEvent(ImagesTopBarEvent.OnBackIconClick) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                } else {
                    IconButton(onClick = { handleEvent(ImagesTopBarEvent.OnBarActiveChange(true)) }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    }
                }
            }
        },
        placeholder = {
            Text(stringResource(R.string.search_images))
        }
    ) {
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(state.loading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 3.dp
                )
            } else if(state.error != null) {
                Spacer(modifier = Modifier.height(16.dp))
                ErrorLabel(
                    modifier = Modifier.fillMaxWidth(),
                    text = state.error.asString()
                ) {
                    handleEvent(ImagesTopBarEvent.OnQueryChange(state.query))
                }
                Text(
                    text = state.error.asString(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                LazyColumn {
                    items(state.suggestions + state.suggestions) { suggestion ->
                        ListItem(
                            modifier = Modifier
                                .clickable {
                                    handleEvent(ImagesTopBarEvent.OnSearch(suggestion))
                                },
                            leadingContent = {
                                Icon(imageVector = Icons.Default.Search, contentDescription = null)
                            },
                            headlineContent = {
                                Text(text = suggestion)
                            }
                        )
                    }
                }
            }
        }
    }
}