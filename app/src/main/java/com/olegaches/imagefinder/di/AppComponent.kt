package com.olegaches.imagefinder.di

import android.content.Context
import androidx.room.Room
import com.olegaches.imagefinder.data.local.ImageDao
import com.olegaches.imagefinder.data.local.ImageDatabase
import com.olegaches.imagefinder.data.remote.ImageSearchApi
import com.olegaches.imagefinder.data.repository.ImageSearchRepositoryImpl
import com.olegaches.imagefinder.domain.repository.ImageSearchRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Component
@Singleton
abstract class AppComponent(private val appContext: Context) {
    @Singleton
    @Provides
    fun ImageSearchRepositoryImpl.bind(): ImageSearchRepository = this

    @Singleton
    @Provides
    fun provideImageSearchApi(): ImageSearchApi {
        return Retrofit
            .Builder()
            .baseUrl(ImageSearchApi.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                ))
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun provideImageDatabase(): ImageDatabase {
        return Room.databaseBuilder(
            context = appContext,
            ImageDatabase::class.java,
            "image.db"
        ).build()
    }
}