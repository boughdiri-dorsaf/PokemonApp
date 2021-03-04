package com.fooddelivery.retrofit.data

import androidx.room.*

@Entity(tableName = "pokemon_table")

data class PokemonEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val categ: String,
    val image: String
)

