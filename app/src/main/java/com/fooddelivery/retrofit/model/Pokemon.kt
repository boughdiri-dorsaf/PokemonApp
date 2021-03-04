package com.fooddelivery.retrofit.model


import com.google.gson.annotations.SerializedName

data class Pokemon(
    val abilities: List<String>? = null,
    val attack: Int = 0,
    @SerializedName("base_exp")
    val baseExp: String = "",
    val category: String,
    val cycles: String= "",
    val defense: Int=0,
    @SerializedName("egg_groups")
    val eggGroups: String= "",
    val evolutions: List<String>? = null,
    val evolvedfrom: String= "",
    @SerializedName("female_percentage")
    val femalePercentage: String= "",
    val genderless: Int = 0,
    val height: String= "",
    val hp: Int = 0,
    val id: String = "",
    val imageurl: String,
    @SerializedName("male_percentage")
    val malePercentage: String = "",
    val name: String ,
    val reason: String = "",
    @SerializedName("special_attack")
    val specialAttack: Int = 0,
    @SerializedName("special_defense")
    val specialDefense: Int = 0,
    val speed: Int = 0,
    val total: Int = 0,
    val typeofpokemon: List<String>? = null,
    val weaknesses: List<String>? = null,
    val weight: String = "",
    val xdescription: String = "",
    val ydescription: String = ""
)