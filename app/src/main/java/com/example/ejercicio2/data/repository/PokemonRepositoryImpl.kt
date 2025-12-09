package com.example.ejercicio2.data.repository

import com.example.ejercicio2.data.PokemonDataSource
import com.example.ejercicio2.data.model.Pokemon
import com.example.ejercicio2.di.ApplicationScope
import com.example.ejercicio2.di.LocalDataSource
import com.example.ejercicio2.di.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    @RemoteDataSource private val remoteDataSource: PokemonDataSource,
    @LocalDataSource private val localDataSource: PokemonDataSource,
    @ApplicationScope private val scope: CoroutineScope
): PokemonRepository {

    override suspend fun readOne(id: Long): Result<Pokemon> {
        return remoteDataSource.readOne(id)
    }

    override suspend fun readAll(): Result<List<Pokemon>> {
        return remoteDataSource.readAll()
    }

    override fun observe(): Flow<Result<List<Pokemon>>> {
        scope.launch {
            refresh()
        }
        return localDataSource.observe()
    }

    private suspend fun refresh() {
        val resultRemotePokemon = remoteDataSource.readAll()
        if (resultRemotePokemon.isSuccess) {
            localDataSource.addAll(resultRemotePokemon.getOrNull()!!)
        }
    }
}