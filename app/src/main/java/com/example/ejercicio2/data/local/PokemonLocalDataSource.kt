package com.example.ejercicio2.data.local

import com.example.ejercicio2.data.PokemonDataSource
import com.example.ejercicio2.data.model.Pokemon
import com.example.ejercicio2.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonLocalDataSource @Inject constructor(
    @ApplicationScope private val scope: CoroutineScope,
    private val pokemonDao: PokemonDao
): PokemonDataSource {

    override suspend fun addAll(pokemonList: List<Pokemon>) {
        withContext(Dispatchers.IO) {
            pokemonList.forEach { pokemon ->
                val entity = pokemon.toEntity()
                pokemonDao.insert(entity)
            }
        }
    }

    override fun observe(): Flow<Result<List<Pokemon>>> {
        return pokemonDao.observeAll().map { entities ->
            Result.success(entities.toModel())
        }
    }

    override suspend fun readAll(): Result<List<Pokemon>> {
        return withContext(Dispatchers.IO) {
            Result.success(pokemonDao.getAll().toModel())
        }
    }

    override suspend fun readOne(id: Long): Result<Pokemon> {
        return withContext(Dispatchers.IO) {
            val entity = pokemonDao.readPokemonById(id)
            if (entity == null) {
                Result.failure(PokemonNotFoundException())
            } else {
                Result.success(entity.toModel())
            }
        }
    }

    override suspend fun isError() {
        TODO("Not yet implemented")
    }
}