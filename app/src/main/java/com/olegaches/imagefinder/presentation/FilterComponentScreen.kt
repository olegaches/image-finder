package com.olegaches.imagefinder.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.olegaches.imagefinder.R
import com.olegaches.imagefinder.domain.enums.ImageSize
import com.olegaches.imagefinder.domain.enums.ImageType

@Composable
fun FilterComponentScreen(filterComponent: IFilterComponent) {
    val state = filterComponent.state.collectAsStateWithLifecycle().value
    val handleEvent = filterComponent::handleEvent
    Scaffold(
        modifier = Modifier.height(450.dp),
        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = { handleEvent(FilterEvent.ApplyFilter) }
            ) {
                Text(stringResource(R.string.show_results))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(bottom = paddingValues.calculateBottomPadding())
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
        ) {
            val imageSize = stringResource(state.size.value)
            val imageType = stringResource(state.type.value)
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = stringResource(R.string.filters),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,

            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = stringResource(R.string.size),
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(9.dp))
            DropDownTextFiled(
                value = imageSize,
                dropDownList = ImageSize.entries,
                dropDownItemText = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = it.value),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                onItemClicked = { handleEvent(FilterEvent.ChangeSize(it)) }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.type),
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(9.dp))
            DropDownTextFiled(
                value = imageType,
                dropDownList = ImageType.entries,
                dropDownItemText = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = it.value),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                onItemClicked = { handleEvent(FilterEvent.ChangeType(it)) }
            )
        }
    }
}

@Composable
fun <T> DropDownTextFiled(
    value: String,
    dropDownList: List<T>,
    dropDownItemText: @Composable (T) -> Unit,
    onItemClicked: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var width by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current
    val shape = remember {
        RoundedCornerShape(13.dp)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { layoutCoordinates ->
                width = layoutCoordinates.size.width
            },
    ) {
        MaterialTheme(
            shapes = MaterialTheme.shapes.copy(extraSmall = shape)
        ) {
            DropdownMenu(
                modifier = Modifier
                    .width(with(density) { width.toDp() })
                    .border(shape = shape, color = Color.Transparent, width = 1.dp)
                    .clip(shape),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                dropDownList.forEach { item ->
                    DropdownMenuItem(
                        text = { dropDownItemText(item) },
                        onClick = {
                            expanded = false
                            onItemClicked(item)
                        }
                    )
                }
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .border(
                width = 1.dp,
                shape = shape,
                color = MaterialTheme.colorScheme.inversePrimary
            )
            .clip(shape)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable { expanded = !expanded }
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = value,
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.outline
        )
    }
}