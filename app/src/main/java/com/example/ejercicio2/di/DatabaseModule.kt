package com.example.ejercicio2.di

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): PokemonDatabase {
        val database = Room.databaseBuilder(
            context = applicationContext,
            klass = PokemonDatabase::class.java,
            name = "pokemon-db"
        ).build()
        return database;
    }
    @Provides
    fun providePokemonDao(
        database: PokemonDatabase
    ): PokemonDao {
        return database.getPokemonDao()
    }
}