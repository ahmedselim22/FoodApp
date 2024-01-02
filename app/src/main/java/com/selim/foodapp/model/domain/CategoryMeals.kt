package com.selim.foodapp.model.domain


import com.google.gson.annotations.SerializedName

data class CategoryMeals(
    @SerializedName("meals")
    val meals: List<CategoryMeal> = listOf()
)