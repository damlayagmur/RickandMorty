package com.damlayagmur.rickandmorty.di

import com.damlayagmur.rickandmorty.network.CharacterService
import com.damlayagmur.rickandmorty.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCharacterRepository(
        characterService: CharacterService,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): CharacterRepository{
        return CharacterRepository(characterService, dispatcher)
    }
}