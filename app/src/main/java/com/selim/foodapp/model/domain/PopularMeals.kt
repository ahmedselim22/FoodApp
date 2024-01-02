package com.selim.foodapp.model.domain


import com.google.gson.annotations.SerializedName

data class PopularMeals(
    @SerializedName("meals")
    val meals: List<PopularMeal> = listOf()
)