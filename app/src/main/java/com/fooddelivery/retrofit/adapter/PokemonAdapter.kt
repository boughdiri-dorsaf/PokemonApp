package com.fooddelivery.retrofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fooddelivery.retrofit.databinding.PokemonItemBinding
import com.fooddelivery.retrofit.model.Pokemon

class PokemonAdapter : ListAdapter<Pokemon, PokemonAdapter.PokemonViewholder>(PokemonDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewholder {
        val binding = PokemonItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )


        return PokemonViewholder(binding)
    }

     override fun onBindViewHolder(holder: PokemonViewholder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

     }


    inner class PokemonViewholder(val binding: PokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemon: Pokemon) {

            val ctx = itemView.context
            with(binding) {
                name.text = pokemon.name
                categ.text = pokemon.category

                Glide.with(ctx)
                    .load(pokemon.imageurl)
                    .centerCrop()
                    .into(binding.image)
            }
        }
    }
}

class PokemonDiffUtils : DiffUtil.ItemCallback<Pokemon>() {

    override fun areItemsTheSame(
        oldItem: Pokemon,
        newItem: Pokemon
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: Pokemon,
        newItem: Pokemon
    ) = oldItem == newItem
}