package com.selim.foodapp.model.domain


import com.google.gson.annotations.SerializedName

data class Meals(
    @SerializedName("meals")
    val meals: List<Meal> = listOf()
)