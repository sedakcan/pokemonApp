package com.sedakcan.pokemons.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sedakcan.pokemons.model.Pokemon
import com.sedakcan.pokemons.service.PokemonAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class PokemonViewModel(application: Application) : BaseViewModel(application) {

    val pokemonLiveData = MutableLiveData<Pokemon>()

    private val pokemonApiService = PokemonAPIService()
    private val disposable = CompositeDisposable()


    fun getData(uuId: Int) {

        launch {
            disposable.add(

                pokemonApiService.getPokemonList()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Pokemon>>() {
                        override fun onSuccess(pokemonList: List<Pokemon>) {
                            for (pokemon in pokemonList) {
                                if (pokemon.pokemonId == uuId) {
                                    pokemonLiveData.value = pokemon
                                    break
                                }
                            }
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                        }
                    })
            )
        }
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }

}