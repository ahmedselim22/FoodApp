package com.selim.foodapp.viewModels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.selim.foodapp.model.database.MealDatabase
import com.selim.foodapp.model.repository.DatabaseRepository
import com.selim.foodapp.model.domain.Meal
import com.selim.foodapp.model.domain.Meals
import com.selim.foodapp.model.networking.API
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(private val db :MealDatabase):ViewModel() {
    private val databaseRepository = DatabaseRepository(db.mealDao())
    val mealInfo =MutableLiveData<Meal>()

    fun getMealbyId(id:String){
        API.apiService.getMealById(id).enqueue(object :Callback<Meals>{
            override fun onResponse(call: Call<Meals>, response: Response<Meals>) {
                if (response.body() !=null){
                    val currentMeal = response.body()!!.meals.first()
                    mealInfo.postValue(currentMeal)
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


    @SuppressLint("CheckResult")
    fun insertNewMeal(meal:Meal){
        viewModelScope.launch{
            databaseRepository.insertNewMeal(meal)
        }
    }
}