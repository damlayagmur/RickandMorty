package com.damlayagmur.rickandmorty.network

import com.damlayagmur.rickandmorty.model.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {

    @GET("api/character")
    suspend fun getCharacterInfo(): Response<Character>

    @GET("api/character/{id}")
    suspend fun getAirInfo(@Path("id") id: String): Response<Character>
}