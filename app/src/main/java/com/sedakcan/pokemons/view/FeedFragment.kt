package com.sedakcan.pokemons.view

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedakcan.pokemons.R
import com.sedakcan.pokemons.adapter.PokemonAdapter
import com.sedakcan.pokemons.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {

    private lateinit var viewModel: FeedViewModel
    private val pokemonAdapter = PokemonAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().title = getString(R.string.app_name)


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        rvPokemonList.layoutManager = LinearLayoutManager(context)
        rvPokemonList.adapter = pokemonAdapter

        swipeRefreshLayout.setOnRefreshListener {
            pokemonLoading.visibility = View.VISIBLE
            pokemonError.visibility = View.GONE
            rvPokemonList.visibility = View.GONE
            viewModel.refreshData()

            swipeRefreshLayout.isRefreshing=false
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.pokemons.observe(viewLifecycleOwner, Observer { pokemons ->
            pokemons?.let {
                rvPokemonList.visibility = View.VISIBLE
                pokemonAdapter.updatePokemonList(pokemons)
            }
        })

        viewModel.pokemonError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    pokemonError.visibility = View.VISIBLE
                    rvPokemonList.visibility = View.GONE
                    pokemonLoading.visibility = View.GONE

                } else {
                    pokemonError.visibility = View.GONE
                }
            }
        })

        viewModel.pokemonLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    pokemonLoading.visibility = View.VISIBLE
                    rvPokemonList.visibility = View.GONE
                    pokemonError.visibility = View.GONE
                } else {
                    pokemonLoading.visibility = View.GONE
                }

            }
        })

    }

}