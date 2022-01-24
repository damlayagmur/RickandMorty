package com.damlayagmur.rickandmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damlayagmur.rickandmorty.model.Character
import com.damlayagmur.rickandmorty.model.Result
import com.damlayagmur.rickandmorty.model.State
import com.damlayagmur.rickandmorty.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) :
    ViewModel() {

    private val _characterList = MutableLiveData<State<List<Result?>?>>()
    val characterListLiveData: LiveData<State<List<Result?>?>>
        get() = _characterList

    fun getCharacter() {
        viewModelScope.launch {
            characterRepository.getCharacterInfo().collect {
                _characterList.value = it
            }
        }
    }
}