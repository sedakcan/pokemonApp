package com.sedakcan.pokemons.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.sedakcan.pokemons.R
import com.sedakcan.pokemons.databinding.FragmentPokemonBinding
import com.sedakcan.pokemons.viewmodel.PokemonViewModel
import kotlinx.android.synthetic.main.item_pokemon.*

class PokemonFragment : Fragment() {

    private lateinit var viewModel: PokemonViewModel
    private var pokemonUuid = 0
    private var pokemonName = ""
    private lateinit var dataBinding: FragmentPokemonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            pokemonUuid = PokemonFragmentArgs.fromBundle(it).pokemonUuid
            pokemonName = PokemonFragmentArgs.fromBundle(it).pokemonName
        }
        requireActivity().title = pokemonName

        viewModel = ViewModelProviders.of(this).get(PokemonViewModel::class.java)
        viewModel.getData(pokemonUuid)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.pokemonLiveData.observe(viewLifecycleOwner, Observer { pokemon ->
            pokemon?.let {
                dataBinding.selectedPokemon = pokemon
            }
        })
    }
}