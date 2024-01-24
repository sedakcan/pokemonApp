package com.sedakcan.pokemons.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sedakcan.pokemons.R
import com.sedakcan.pokemons.adapter.PokemonAdapter.PokemonViewHolder
import com.sedakcan.pokemons.databinding.ItemPokemonBinding
import com.sedakcan.pokemons.model.Pokemon
import com.sedakcan.pokemons.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_pokemon.view.*

class PokemonAdapter(val pokemonList: ArrayList<Pokemon>) : RecyclerView.Adapter<PokemonViewHolder>(),PokemonClickListener {

    class PokemonViewHolder(var view: ItemPokemonBinding) : RecyclerView.ViewHolder(view.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemPokemonBinding>(
            inflater,
            R.layout.item_pokemon,
            parent,
            false
        )
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.view.pokemon = pokemonList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun updatePokemonList(newPokemonList: List<Pokemon>) {
        pokemonList.clear()
        pokemonList.addAll(newPokemonList)
        notifyDataSetChanged()
    }

    override fun onPokemonClicked(v: View) {
        val pokemonId=v.tvPokemonId.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToPokemonFragment()
        action.pokemonUuid = pokemonId
        action.pokemonName= v.tvPokemonName.text.toString()
        Navigation.findNavController(v).navigate(action)
    }
}