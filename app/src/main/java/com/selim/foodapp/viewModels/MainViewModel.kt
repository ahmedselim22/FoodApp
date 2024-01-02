package com.selim.foodapp.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selim.foodapp.model.database.MealDatabase
import com.selim.foodapp.model.repository.DatabaseRepository
import com.selim.foodapp.model.domain.*
import com.selim.foodapp.model.networking.API
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val mealDatabase: MealDatabase): ViewModel() {
    val randomMeal =MutableLiveData<Meal>()
    val popularMeals = MutableLiveData<List<PopularMeal>>()
    val categories = MutableLiveData<List<Category>>()
    val favoriteMeals = MutableLiveData<List<Meal>>()
    val searchedMeals = MutableLiveData<List<Meal>>()
    private val databaseRepository = DatabaseRepository(mealDatabase.mealDao())
    val mealDetails =MutableLiveData<Meal>()

    init {
        getRandomMeal()
        getPopularMeals()
        getCategories()
        getFavoriteMeals()
    }
    private fun getRandomMeal(){
        API.apiService.getRandomMeal().enqueue(object :Callback<Meals>{
            override fun onResponse(call: Call<Meals>, response: Response<Meals>) {
                if (response.body() !=null){
                    val currentMeal = response.body()!!.meals[0]
                    randomMeal.postValue(currentMeal)
                    Log.d("here", response.toString())
                }
                else{
                    Log.d("here", response.toString())
                }
            }

            override fun onFailure(call: Call<Meals>, t: Throwable) {
                Log.d("here", t.message.toString())
            }

        })
    }

    private fun getPopularMeals(){
        val array = arrayOf("Beef","Breakfast","Chicken","Dessert","Miscellaneous","Pasta","Pork","Seafood","Side","Starter","Vegan","Vegetarian")
        val categoryIndex = (0 until array.size).random()
        val category =array[categoryIndex]
        API.apiService.getPopularMeals(category).enqueue(object :Callback<PopularMeals>{
            override fun onResponse(call: Call<PopularMeals>, response: Response<PopularMeals>) {
                if (response.body() !=null){
                    val popularMeal = response.body()!!.meals
                    popularMeals.postValue(popularMeal)
                    Log.d("here", response.toString())
                }
                else{
                    Log.d("here else", response.toString())
                }
            }

            override fun onFailure(call: Call<PopularMeals>, t: Throwable) {
                Log.d("here popularMeals", t.message.toString())
            }

        })
    }

    private fun getCategories(){
        API.apiService.getCategories().enqueue(object :Callback<Categories>{
            override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                response.body()!!.categories.let {
                    categoriesList ->
                    Log.d("here categories", categoriesList.toString())
                    categories.postValue(categoriesList)
                }
            }

            override fun onFailure(call: Call<Categories>, t: Throwable) {
                Log.d("here categories", t.message.toString())
            }

        })
    }

    fun getMealsById(id:String){
        API.apiService.getMealById(id).enqueue(object :Callback<Meals>{
            override fun onResponse(call: Call<Meals>, response: Response<Meals>) {
                val meal = response.body()!!.meals[0]
                mealDetails.postValue(meal)
            }

            override fun onFailure(call: Call<Meals>, t: Throwable) {
                Log.d("here", t.message.toString())
            }

        })
    }

    fun getSearchedMeals(searchQuery:String){
        API.apiService.getSearchedMeals(searchQuery).enqueue(object :Callback<Meals>{
            override fun onResponse(call: Call<Meals>, response: Response<Meals>) {
                response.let {
                    mealsList->
                    Log.d("searched meals viewModel", mealsList.toString())
                    searchedMeals.postValue(mealsList.body()!!.meals)
                }
            }
            override fun onFailure(call: Call<Meals>, t: Throwable) {
                Log.d("here searched meals", t.message.toString())
            }
        })
    }

    fun getFavoriteMeals(){
        viewModelScope.launch {
            val meals =databaseRepository.getFavoriteMeals()
            favoriteMeals.postValue(meals)
        }
    }

    fun deleteFavoriteMeal(meal: Meal){
        viewModelScope.launch {
            databaseRepository.deleteMeal(meal)
        }
    }

    fun insertNewMeal(meal:Meal){
        viewModelScope.launch{
            databaseRepository.insertNewMeal(meal)
        }
    }



}