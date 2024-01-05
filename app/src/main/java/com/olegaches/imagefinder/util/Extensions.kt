package com.olegaches.imagefinder.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.olegaches.imagefinder.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

fun LifecycleOwner.coroutineScope(context: CoroutineContext): CoroutineScope {
    val scope = CoroutineScope(context)
    lifecycle.doOnDestroy(scope::cancel)
    return scope
}

@Composable
fun Dp.toPx() = with(LocalDensity.current) { this@toPx.toPx() }


@Composable
fun Int.toDp() = with(LocalDensity.current) { this@toDp.toDp() }

fun IntSize.toDpSize(density: Density): DpSize {
    with(density) {
        return DpSize(width = width.toDp(), height = height.toDp())
    }
}

@Composable
fun <T> Flow<T>.observeAsEvents(onEvent: suspend (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(this, lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@observeAsEvents.collect(onEvent)
        }
    }
}

fun handleHttpCallException(throwable: Throwable): UiText {
    return when(throwable) {
        is HttpException -> {
            val localizedMessage = throwable.localizedMessage
            if(localizedMessage.isNullOrEmpty()) {
                UiText.StringResource(R.string.unknown_exception)
            }
            else {
                UiText.DynamicString(localizedMessage)
            }
        }
        is IOException -> {
            UiText.StringResource(R.string.io_exception)
        }
        else -> UiText.StringResource(R.string.unknown_exception)
    }
}