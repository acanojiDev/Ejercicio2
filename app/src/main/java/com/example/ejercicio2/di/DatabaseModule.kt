package com.example.ejercicio2.di

import android.content.Context
import androidx.room.Room
import com.example.ejercicio2.data.local.PokemonDao
import com.example.ejercicio2.data.local.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): PokemonDatabase {
        return Room.databaseBuilder(
            context = applicationContext,
            klass = PokemonDatabase::class.java,
            name = "pokemon-db"
        ).build()
    }

    @Provides
    fun providePokemonDao(
        database: PokemonDatabase
    ): PokemonDao {
        return database.getPokemonDao()
    }
}