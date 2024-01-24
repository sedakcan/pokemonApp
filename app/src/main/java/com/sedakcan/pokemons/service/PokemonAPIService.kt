package com.sedakcan.pokemons.service

import com.sedakcan.pokemons.model.Pokemon
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PokemonAPIService {

    private val BASE_URL = "https://gist.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(PokemonAPI::class.java)


     fun getPokemonList(): Single<List<Pokemon>> {
        return api.getPokemons()
    }
}