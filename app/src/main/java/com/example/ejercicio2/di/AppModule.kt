package com.example.ejercicio2.di

import com.google.android.datatransport.runtime.dagger.Module

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Singleton
    @Binds
    @RemoteDataSource
    abstract fun bindsRemotePokemonRemoteDataSource(ds: PokemonRemoteDataSource): PokemonDataSource

    @Singleton
    @Binds
    @LocalDataSource
    abstract fun bindsLocalPokemonRemoteDataSource(ds: PokemonLocalDataSource): PokemonDataSource

    @Binds
    @Singleton
    abstract fun bindPokemonRepository(repository: PokemonRepositoryImpl): PokemonRepository
    //abstract fun bindPokemonRepository(repository: PokemonFakeRemoteRepository): PokemonRepository
    //abstract fun bindPokemonRepository(repository: PokemonInMemoryRepository): PokemonRepository
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource