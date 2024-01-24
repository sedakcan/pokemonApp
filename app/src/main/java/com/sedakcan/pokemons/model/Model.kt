package com.sedakcan.pokemons.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("id")
    val pokemonId: Int?,
    @SerializedName("name")
    val pokemonName: String?,
    @SerializedName("description")
    val pokemonDescription: String?,
    @SerializedName("imageUrl")
    val pokemonImageUrl: String?
)