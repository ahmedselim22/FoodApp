package com.selim.foodapp.model.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverters {
    @TypeConverter
    fun fromAnyToString(any: Any?):String{
        if (any==null){
            return ""
        }
        return any.toString()
    }

    @TypeConverter
    fun fromStringToAny(string: String?):Any{
        if (string==null){
            return ""
        }
        return string
    }

}