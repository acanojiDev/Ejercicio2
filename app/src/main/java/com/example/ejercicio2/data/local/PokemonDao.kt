package com.example.ejercicio2.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemon: PokemonEntity)

    @Delete
    suspend fun delete(pokemon:PokemonEntity): Int

    @Query("SELECT * FROM pokemon")
    suspend fun getAll(): List<PokemonEntity>

    @Query("SELECT * FROM pokemon")
    fun observeAll(): Flow<List<PokemonEntity>>

    @Query
    suspend fun readPokemonById(id: Long):PokemonEntity?
}