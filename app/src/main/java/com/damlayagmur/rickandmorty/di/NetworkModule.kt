package com.damlayagmur.rickandmorty.di

import com.damlayagmur.rickandmorty.network.CharacterService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideCharacterService(moshi: Moshi): CharacterService {
        val retrofit = Retrofit.Builder().baseUrl("https://rickandmortyapi.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
        return retrofit.create(CharacterService::class.java)
    }
}