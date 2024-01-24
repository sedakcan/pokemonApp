package com.sedakcan.pokemons.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sedakcan.pokemons.model.Pokemon
import com.sedakcan.pokemons.service.PokemonAPIService
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : BaseViewModel(application) {
    private val pokemonApiService = PokemonAPIService()
    private val disposable = CompositeDisposable()

    val pokemons = MutableLiveData<List<Pokemon>>()
    val pokemonError = MutableLiveData<Boolean>()
    val pokemonLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        pokemonLoading.value = true
        launch {

            disposable.add(
                pokemonApiService.getPokemonList()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Pokemon>>() {
                        override fun onSuccess(t: List<Pokemon>) {
                            pokemons.value = t
                            pokemonError.value = false
                            pokemonLoading.value = false
                        }

                        override fun onError(e: Throwable) {
                            pokemonError.value = true
                            pokemonLoading.value = false
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