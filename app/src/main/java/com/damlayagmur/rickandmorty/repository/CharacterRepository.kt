package com.damlayagmur.rickandmorty.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.damlayagmur.rickandmorty.di.IoDispatcher
import com.damlayagmur.rickandmorty.model.Character
import com.damlayagmur.rickandmorty.model.Result
import com.damlayagmur.rickandmorty.model.State
import com.damlayagmur.rickandmorty.network.CharacterService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CharacterRepository @Inject constructor(
    private val characterService: CharacterService,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) {
    /*suspend fun characterInfo(): MutableLiveData<Character> {

        val characterResponse = MutableLiveData<Character>()
        val apiResponse = characterService.getCharacterInfo()

        if (apiResponse.isSuccessful) {
            characterResponse.value = apiResponse.body()
        } else {
            Log.d("else burasi", "olmamis demek dikkat")
        }
        return characterResponse
    }*/

    fun getCharacterInfo() = flow {
        emit(State.Loading<List<Result?>?>())

        try {
            val apiResponse = characterService.getCharacterInfo()
            val characterResponse = apiResponse.body()

            if (apiResponse.isSuccessful) {
                emit(characterResponse?.let { State.success<List<Result?>?>(it.results) }
                )
            } else {
                emit(State.error<List<Result?>?>(apiResponse.message()))
            }
        } catch (e: Exception) {
            emit(State.error<List<Result?>?>(e.localizedMessage))
        }
    }.flowOn(dispatcher)
}