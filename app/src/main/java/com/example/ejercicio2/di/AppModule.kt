package com.example.ejercicio2.di

import com.example.ejercicio2.data.PokemonDataSource
import com.example.ejercicio2.data.local.PokemonLocalDataSource
import com.example.ejercicio2.data.remote.PokemonRemoteDataSource
import com.example.ejercicio2.data.repository.PokemonRepository
import com.example.ejercicio2.data.repository.PokemonRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    @RemoteDataSource
    abstract fun bindsRemotePokemonDataSource(
        ds: PokemonRemoteDataSource
    ): PokemonDataSource

    @Singleton
    @Binds
    @LocalDataSource
    abstract fun bindsLocalPokemonDataSource(
        ds: PokemonLocalDataSource
    ): PokemonDataSource

    @Binds
    @Singleton
    abstract fun bindPokemonRepository(
        repository: PokemonRepositoryImpl
    ): PokemonRepository
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource