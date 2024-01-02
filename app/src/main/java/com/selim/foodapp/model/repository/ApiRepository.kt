//package com.selim.foodapp.model.repository
//
//import android.util.Log
//import com.selim.foodapp.model.domain.*
//import com.selim.foodapp.model.networking.API
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class ApiRepository {
//    fun getMealsByCategory(categoryName:String):List<CategoryMeal>{
//        var list : List<CategoryMeal>? =null
//        API.apiService.getCategoryMeals(categoryName).enqueue(object : Callback<CategoryMeals> {
//            override fun onResponse(call: Call<CategoryMeals>, response: Response<CategoryMeals>) {
//                if (response.body() !=null){
//                    val currentCategory = response.body()?.meals
//                    list = currentCategory!!
//                    Log.d("here", response.toString())
//                }
//                else{
//                    Log.d("here", response.toString())
//                }
//            }
//            override fun onFailure(call: Call<CategoryMeals>, t: Throwable) {
//                Log.d("here category viewModel", t.message.toString())
//            }
//        })
//        return list!!
//    }
//
//    fun getRandomMeal():Meal{
//        var meal : Meal? =null
//        API.apiService.getRandomMeal().enqueue(object :Callback<Meals>{
//            override fun onResponse(call: Call<Meals>, response: Response<Meals>) {
//                if (response.body() !=null){
//                    val currentMeal = response.body()!!.meals[0]
//                    meal = currentMeal
//                    Log.d("here", response.toString())
//                }
//                else{
//                    Log.d("here", response.toString())
//                }
//            }
//            override fun onFailure(call: Call<Meals>, t: Throwable) {
//                Log.d("here", t.message.toString())
//            }
//        })
//        return meal!!
//    }
//
//    fun getPopularMeals():List<PopularMeal>{
//        var list:List<PopularMeal>? =null
//        val array = arrayOf("Beef","Breakfast","Chicken","Dessert","Miscellaneous","Pasta","Pork","Seafood","Side","Starter","Vegan","Vegetarian")
//        val categoryIndex = (0 until array.size).random()
//        val category =array[categoryIndex]
//        API.apiService.getPopularMeals(category).enqueue(object :Callback<PopularMeals>{
//            override fun onResponse(call: Call<PopularMeals>, response: Response<PopularMeals>) {
//                if (response.body() !=null){
//                    val popularMeals = response.body()!!.meals
//                    list = popularMeals
//                    Log.d("here", response.toString())
//                }
//                else{
//                    Log.d("here else", response.toString())
//                }
//            }
//            override fun onFailure(call: Call<PopularMeals>, t: Throwable) {
//                Log.d("here popularMeals", t.message.toString())
//            }
//        })
//        return list!!
//    }
//
//    fun getCategories():List<Category>{
//        var list:List<Category>? =null
//        API.apiService.getCategories().enqueue(object :Callback<Categories>{
//            override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
//                response.body()!!.categories.let {
//                        categoriesList ->
//                    Log.d("here categories", categoriesList.toString())
//                    list = categoriesList
//                }
//            }
//
//            override fun onFailure(call: Call<Categories>, t: Throwable) {
//                Log.d("here categories", t.message.toString())
//            }
//        })
//        return list!!
//    }
//
//    fun getMealsById(id:String):Meal{
//        var meala:Meal? =null
//        API.apiService.getMealById(id).enqueue(object :Callback<Meals>{
//            override fun onResponse(call: Call<Meals>, response: Response<Meals>) {
//                val meal = response.body()!!.meals[0]
//                meala = meal
//            }
//
//            override fun onFailure(call: Call<Meals>, t: Throwable) {
//                Log.d("here", t.message.toString())
//            }
//        })
//        return meala!!
//    }
//
//    fun getSearchedMeals(searchQuery:String):List<Meal>{
//        var list :List<Meal>?=null
//        API.apiService.getSearchedMeals(searchQuery).enqueue(object :Callback<Meals>{
//            override fun onResponse(call: Call<Meals>, response: Response<Meals>) {
//                response.let {
//                        mealsList->
//                    Log.d("searched meals viewModel", mealsList.toString())
//                    list = mealsList.body()!!.meals
//                }
//            }
//            override fun onFailure(call: Call<Meals>, t: Throwable) {
//                Log.d("here searched meals", t.message.toString())
//            }
//        })
//        return list!!
//    }
////move to mainViewModel
////    private fun getRandomMeal(){
////        val meals =repository.getRandomMeal()
////        randomMeal.postValue(meals)
////    }
////
////    private fun getPopularMeals(){
////        val meals = repository.getPopularMeals()
////        popularMeals.postValue(meals)
////    }
////
////    private fun getCategories(){
////        val categoriesList = repository.getCategories()
////        categories.postValue(categoriesList)
////    }
////
////    fun getMealsById(id:String){
////        val meal =repository.getMealsById(id)
////        mealDetails.postValue(meal)
////    }
////
////    fun getSearchedMeals(searchQuery:String){
////        val meals =repository.getSearchedMeals(searchQuery)
////        searchedMeals.postValue(meals)
////    }
//}