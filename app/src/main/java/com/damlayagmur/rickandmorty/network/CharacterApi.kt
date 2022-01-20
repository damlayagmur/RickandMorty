package com.damlayagmur.rickandmorty.network

import com.damlayagmur.rickandmorty.model.Character
import com.damlayagmur.rickandmorty.model.Info
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CharacterApi {

    @GET("api/character")
    suspend fun getCharacterInfo(): Response<Character>

    companion object {
        operator fun invoke(): CharacterApi {
            return Retrofit.Builder().baseUrl("https://rickandmortyapi.com")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(CharacterApi::class.java)
        }
    }
}