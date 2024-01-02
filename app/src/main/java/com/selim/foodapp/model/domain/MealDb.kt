package com.selim.foodapp.model.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
//@Entity(tableName = "meals")
data class MealDb(
    @PrimaryKey
    val idMeal :Int,
    val strArea: String,
    val strCategory: String,
    val strInstructions: String,
    val strMeal: String,
    val strMealThumb: String,
    val strSource: String,
    val strYoutube: String
)
