package com.selim.foodapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.selim.foodapp.model.domain.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealTypeConverters::class)
abstract class MealDatabase :RoomDatabase(){
    abstract fun mealDao():MealDao
    companion object{
        @Volatile
        private var instance :MealDatabase? =null
        @Synchronized
        fun getInstance(context:Context):MealDatabase{
            if (instance==null){
                instance = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "mealDatabase"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance as MealDatabase
        }
    }
}