//package com.selim.foodapp.utils
//
//import androidx.recyclerview.widget.DiffUtil
//import com.selim.foodapp.model.domain.Meal
//
//class FavoriteDiffUtil(private val oldList:List<Meal>,private val newList: List<Meal>) :DiffUtil.Callback() {
//    override fun getOldListSize(): Int = oldList.size
//
//    override fun getNewListSize(): Int = newList.size
//
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        if (oldList[oldItemPosition].idMeal ==newList[newItemPosition].idMeal
//            && oldList[newItemPosition].idMeal == newList[newItemPosition].idMeal
//        ){return true}
//        return false
//    }
//
//    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return true
//    }
//}