package com.selim.foodapp.model.domain


import com.google.gson.annotations.SerializedName

data class PopularMeal(
    @SerializedName("idMeal")
    val idMeal: String = "",
    @SerializedName("strMeal")
    val strMeal: String = "",
    @SerializedName("strMealThumb")
    val strMealThumb: String = ""
)