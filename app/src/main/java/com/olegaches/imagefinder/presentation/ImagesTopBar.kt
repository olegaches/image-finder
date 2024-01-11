package com.olegaches.imagefinder.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.olegaches.imagefinder.R
import com.olegaches.imagefinder.presentation.composables.ErrorLabel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesTopBar(topBarComponent: IImagesTopBarComponent) {
    val state = topBarComponent.state.collectAsStateWithLifecycle().value
    val searchBarActive = state.searchBarActive
    val handleEvent = topBarComponent::handleEvent
    val focusRequester = remember { FocusRequester() }
    val searchBarPadding by animateDpAsState(
        targetValue = if(searchBarActive) 0.dp else 24.dp,
        animationSpec = tween(700),
        label = ""
    )
    Column {
        SearchBar(
            modifier = Modifier
                .padding(horizontal = searchBarPadding)
                .fillMaxWidth()
                .focusRequester(focusRequester),
            query = state.query,
            onQueryChange = { handleEvent(ImagesTopBarEvent.OnQueryChange(it)) },
            onSearch = { handleEvent(ImagesTopBarEvent.OnSearch(it)) },
            active = searchBarActive,
            onActiveChange = { handleEvent(ImagesTopBarEvent.OnBarActiveChange(it)) },
            colors = SearchBarDefaults.colors(
                dividerColor = MaterialTheme.colorScheme.outline
            ),
            trailingIcon = {
                AnimatedContent(
                    targetState = searchBarActive to state.query.isBlank(),
                    label = "",
                    transitionSpec = {
                        ContentTransform(scaleIn(), scaleOut())
                    }
                ) { (searchBarActive, isBlank) ->
                    if(searchBarActive && !isBlank) {
                        IconButton(onClick = { handleEvent(ImagesTopBarEvent.OnQueryChange("")) }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = null
                            )
                        }
                    } else if (!searchBarActive && !isBlank) {
                        IconButton(onClick = {
                            handleEvent(ImagesTopBarEvent.OnFilterClicked)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.filter_icon),
                                contentDescription = null
                            )
                        }
                    }
                }
            },
            leadingIcon = {
                AnimatedContent(
                    targetState = searchBarActive,
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
                        IconButton(onClick = {
                            handleEvent(ImagesTopBarEvent.OnBarActiveChange(true))
                            focusRequester.requestFocus()
                        }) {
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
        Spacer(modifier = Modifier.height(9.dp))
        Divider()
    }
}