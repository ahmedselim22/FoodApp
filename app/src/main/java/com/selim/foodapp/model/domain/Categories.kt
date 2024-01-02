package com.selim.foodapp.model.domain


import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("categories")
    val categories: List<Category> = listOf()
)