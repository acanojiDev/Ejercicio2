package com.example.ejercicio2.data.local

import com.example.ejercicio2.data.PokemonDataSource
import com.example.ejercicio2.data.model.Pokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonLocalDataSource @Inject constructor(
    private val scope: CoroutineScope,
    private val pokemonDao: PokemonDao
): PokemonDataSource {
    override suspend fun addAll(pokemonList: List<Pokemon>){
        val mutex = Mutex()
        pokemonList.forEach { pokemon ->
            val entity = pokemon.toEntity()
            withContext(Dispatchers.IO){
                pokemonDao.insert(entity)
            }
        }
    }

    override fun observe(): Flow<Result<List<Pokemon>>> {
        val databaseFlow = pokemonDao.observeAll()
        return databaseFlow.map { entities ->
            Result.success(entities.toModel())
        }
    }

    override suspend fun readAll(): Result<List<Pokemon>> {
        val result = Result.success(pokemonDao.getAll().toModel())
        return result
    }

    override suspend fun readOne(id: Long): Result<Pokemon> {
        val entity = pokemonDao.readPokemonById(id)
        return if (entity==null)
            Result.failure(PokemonNotFoundException())
        else
            Result.success(entity.toModel())
    }

    override suspend fun isError() {
        TODO("Not yet implemented")
    }
}