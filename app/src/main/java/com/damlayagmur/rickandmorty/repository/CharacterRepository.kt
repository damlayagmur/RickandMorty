package com.damlayagmur.rickandmorty.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.damlayagmur.rickandmorty.model.Character
import com.damlayagmur.rickandmorty.network.CharacterApi

class CharacterRepository {
    suspend fun characterInfo(): MutableLiveData<Character> {
        val characterResponse = MutableLiveData<Character>()

        val apiResponse = CharacterApi().getCharacterInfo()

        if (apiResponse.isSuccessful) {
            characterResponse.value = apiResponse.body()
        } else {
            Log.d("else burasi", "olmamis demek dikkat")
        }
        return characterResponse
    }
}