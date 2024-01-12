package com.olegaches.imagefinder.domain.util

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val message: UiText, val data: T? = null) : Resource<T>
}
