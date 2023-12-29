package com.olegaches.imagefinder.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset

data class ImagePositionalParam(
    val size: DpSize,
    val offset: IntOffset,
    val paddingValues: PaddingValues,
    val index: Int
)