package com.damlayagmur.rickandmorty.repository

import com.damlayagmur.rickandmorty.di.IoDispatcher
import com.damlayagmur.rickandmorty.model.Result
import com.damlayagmur.rickandmorty.model.State
import com.damlayagmur.rickandmorty.network.CharacterService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CharacterRepository @Inject constructor(
    private val characterService: CharacterService,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) {

    fun getCharacterInfo() = flow {
        emit(State.Loading<List<Result?>?>())

        try {
            val apiResponse = characterService.getCharacterInfo()
            val characterResponse = apiResponse.body()

            if (apiResponse.isSuccessful) {
                emit(characterResponse?.let { State.success(it.results) }
                )
            } else {
                emit(State.error(apiResponse.message()))
            }
        } catch (e: Exception) {
            emit(State.error(e.localizedMessage))
        }
    }.flowOn(dispatcher)

    fun getInfo() = flow {
        emit(State.Loading<List<String?>?>())

        try {
            val apiResponse = characterService.getCharacterInfo()
            val characterResponse = apiResponse.body()

            if (apiResponse.isSuccessful) {
                emit(characterResponse?.let { State.success(it.info.next) }
                )
            } else {
                emit(State.error(apiResponse.message()))
            }
        } catch (e: Exception) {
            emit(State.error(e.localizedMessage))
        }
    }.flowOn(dispatcher)
}