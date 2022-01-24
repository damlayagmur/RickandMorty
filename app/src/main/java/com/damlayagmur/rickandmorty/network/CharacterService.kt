package com.damlayagmur.rickandmorty.network

import com.damlayagmur.rickandmorty.model.Character
import com.damlayagmur.rickandmorty.model.Result
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import javax.inject.Singleton


interface CharacterService {

    @GET("api/character")
    suspend fun getCharacterInfo(): Response<Character>
}