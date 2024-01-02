package com.selim.foodapp.model.networking

import com.selim.foodapp.model.domain.Categories
import com.selim.foodapp.model.domain.CategoryMeals
import com.selim.foodapp.model.domain.Meals
import com.selim.foodapp.model.domain.PopularMeals
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {
    @GET("random.php")
    fun getRandomMeal():Call<Meals>

    @GET("lookup.php?")
    fun getMealById(@Query("i") id:String):Call<Meals>

    //here we use get random category meals instead of get popular meals cause it's premium
    @GET("filter.php?")
    fun getPopularMeals(@Query("c") categoryName:String):Call<PopularMeals>

    @GET("categories.php")
    fun getCategories():Call<Categories>

    @GET("filter.php?")
    fun getCategoryMeals(@Query("c") categoryName:String):Call<CategoryMeals>

    @GET("search.php?")
    fun getSearchedMeals(@Query("s") searchQuery:String):Call<Meals>
}