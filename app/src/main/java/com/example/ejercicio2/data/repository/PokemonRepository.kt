package com.example.ejercicio2.data.repository

import com.example.ejercicio2.data.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun readOne(id:Long): Result<Pokemon>
    suspend fun readAll(): Result<List<Pokemon>>
    fun observe(): Flow<Result<List<Pokemon>>>
}