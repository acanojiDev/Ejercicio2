package com.example.ejercicio2.data.remote

import com.example.ejercicio2.data.PokemonDataSource
import com.example.ejercicio2.data.model.Pokemon
import com.example.ejercicio2.data.remote.model.PokemonRemote
import com.example.ejercicio2.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class PokemonRemoteDataSource @Inject constructor(
    private val api: PokemonApi,
    @ApplicationScope private val scope: CoroutineScope
): PokemonDataSource {

    override suspend fun addAll(pokemonList: List<Pokemon>) {
        TODO("Not yet implemented")
    }

    override fun observe(): Flow<Result<List<Pokemon>>> {
        return flow {
            emit(Result.success(listOf<Pokemon>()))
            val result = readAll()
            emit(result)
        }.shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000L),
            replay = 1
        )
    }

    override suspend fun readAll(): Result<List<Pokemon>> {
        return try {
            val response = api.readAll(limit = 40, offset = 0)
            val finalList = mutableListOf<Pokemon>()

            if (response.isSuccessful) {
                val body = response.body()!!
                for (result in body.results) {
                    val remotePokemon = readOne(name = result.name)
                    remotePokemon?.let {
                        finalList.add(it)
                    }
                }
                Result.success(finalList)
            } else {
                Result.failure(RuntimeException("Error ${response.code()}"))
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    private suspend fun readOne(name: String): Pokemon? {
        return try {
            val response = api.readOne(name)
            if (response.isSuccessful) {
                response.body()?.toExternal()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun readOne(id: Long): Result<Pokemon> {
        return try {
            val response = api.readOne(id)
            if (response.isSuccessful) {
                val pokemon = response.body()!!.toExternal()
                Result.success(pokemon)
            } else {
                Result.failure(RuntimeException("Error ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isError() {
        TODO("Not yet implemented")
    }
}

fun PokemonRemote.toExternal(): Pokemon {
    return Pokemon(
        id = this.id,
        name = this.name,
        sprite = this.sprites.front_default,
        artwork = this.sprites.other.officialArtwork.front_default
    )
}