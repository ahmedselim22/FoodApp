package com.selim.foodapp.model.repository

import com.selim.foodapp.model.database.MealDao
import com.selim.foodapp.model.domain.Meal

class DatabaseRepository(private val dao: MealDao) {
    suspend fun insertNewMeal(meal: Meal){
        dao.insertMeal(meal)
    }

    suspend fun deleteMeal(meal: Meal){
        dao.deleteMeal(meal)
    }

    suspend fun getFavoriteMeals():List<Meal>{
        return dao.getFavoriteMeals()
    }
}