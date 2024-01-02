package com.selim.foodapp.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.selim.foodapp.model.domain.CategoryMeal
import com.selim.foodapp.model.domain.CategoryMeals
import com.selim.foodapp.model.networking.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel:ViewModel() {
    val categories =MutableLiveData<List<CategoryMeal>>()

    fun getMealsByCategory(categoryName:String){
        API.apiService.getCategoryMeals(categoryName).enqueue(object :Callback<CategoryMeals>{
            override fun onResponse(call: Call<CategoryMeals>, response: Response<CategoryMeals>) {
                if (response.body() !=null){
                    val currentCategory = response.body()?.meals
                    categories.postValue(currentCategory!!)
                    Log.d("here", response.toString())
                }
                else{
                    Log.d("here", response.toString())
                }
            }

            override fun onFailure(call: Call<CategoryMeals>, t: Throwable) {
                Log.d("here category viewModel", t.message.toString())
            }

        })
    }
}