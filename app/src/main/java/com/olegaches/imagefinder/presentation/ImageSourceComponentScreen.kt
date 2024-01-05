package com.olegaches.imagefinder.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSourceComponentScreen(imageSourceComponent: IImageSourceComponent) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(9.dp),
                title = {},
                navigationIcon = {
                    IconButton(onClick = imageSourceComponent::onCloseClicked) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        val state = rememberWebViewState(imageSourceComponent.url)
        WebView(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            state = state,
            onCreated = { it.settings.javaScriptEnabled = true },
            captureBackPresses = true
        )
    }
}