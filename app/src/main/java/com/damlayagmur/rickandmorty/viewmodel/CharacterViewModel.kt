package com.damlayagmur.rickandmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.damlayagmur.rickandmorty.model.Character
import com.damlayagmur.rickandmorty.repository.CharacterRepository

class CharacterViewModel : ViewModel() {
    lateinit var response: MutableLiveData<Character>

    suspend fun getCharacterInfoVM(): LiveData<Character>{
        response = CharacterRepository().characterInfo()
        return response
    }
}