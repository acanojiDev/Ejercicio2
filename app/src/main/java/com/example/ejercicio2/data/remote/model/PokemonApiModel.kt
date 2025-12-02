package com.example.ejercicio2.data.remote.model

import com.google.gson.annotations.SerializedName

data class PokemonListRemote(
    val results: List<PokemonListItemRemote>
)

data class PokemonListItemRemote(
    val name: String,
    val url: String
)

data class PokemonRemote(
    val id: Long,
    val name: String,
    val sprites: PokemonSprites,
)

data class PokemonSprites(
    val front_default: String,
    val other: PokemonOtherSprite
)

data class PokemonOtherSprite(
    @SerializedName("official-artwork")
    val officialArtwork: PokemonOfficialArtwork
)

data class PokemonOfficialArtwork(
    val front_default: String
)