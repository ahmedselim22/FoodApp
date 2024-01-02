package com.selim.foodapp.model.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API {
    private val BASE_URL ="https://www.themealdb.com/api/json/v1/1/"
    private val retrofit =Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(MealsApi::class.java)
}