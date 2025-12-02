package com.example.ejercicio2.data

import com.example.ejercicio2.data.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    suspend fun addAll(pokemonList: List<Pokemon>)
    fun observe(): Flow<Result<List<Pokemon>>>
    suspend fun readAll(): Result<List<Pokemon>>
    suspend fun readOne(id: Long): Result<Pokemon>
    suspend fun isError()
}