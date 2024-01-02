package com.selim.foodapp.model.database

import androidx.room.*
import com.selim.foodapp.model.domain.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeal(meal: Meal)

    @Update
    fun updateMeal(meal:Meal)

    @Delete
    fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM mealsData")
    fun getFavoriteMeals():List<Meal>
}
