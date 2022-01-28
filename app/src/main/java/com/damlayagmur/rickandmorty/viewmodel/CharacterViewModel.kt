package com.damlayagmur.rickandmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damlayagmur.rickandmorty.model.Info
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


    /*private val _infoList = MutableLiveData<State<List<Info?>?>>()
    val infoListLiveData: LiveData<State<List<Info?>?>>
        get() = _infoList*/

    fun getCharacter() {
        viewModelScope.launch {
            characterRepository.getCharacterInfo().collect {
                _characterList.value = it as State<List<Result?>?>?
            }
        }
    }

    /*fun getInfo() {
        viewModelScope.launch {
            characterRepository.getInfo().collect {
                _infoList.value = it as State<List<Info?>?>?
            }
        }
    }*/
}