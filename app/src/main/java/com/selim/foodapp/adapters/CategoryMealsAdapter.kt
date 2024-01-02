package com.selim.foodapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.selim.foodapp.R
import com.selim.foodapp.databinding.CategoryMealItemLayoutBinding
import com.selim.foodapp.model.domain.CategoryMeal


class CategoryMealsAdapter:RecyclerView.Adapter<CategoryMealsAdapter.CategorieMealsViewHolder>()  {
    private var list =ArrayList<CategoryMeal>()
    lateinit var onCategoryItemClicked :((CategoryMeal)->Unit)
    @SuppressLint("NotifyDataSetChanged")
    fun setCategoryMeals(newList: ArrayList<CategoryMeal>){
        this.list =newList
        notifyDataSetChanged()
    }

    class CategorieMealsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = CategoryMealItemLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorieMealsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_meal_item_layout,parent,false)
        return CategorieMealsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategorieMealsViewHolder, position: Int) {
        val meals = list[position]
        holder.binding.apply {
            tvCategoryMealName.text = meals.strMeal
        }
        Glide.with(holder.itemView).load(meals.strMealThumb).into(holder.binding.ivCategoryMealImage)
        holder.binding.cardViewCategoryMeal.setOnClickListener {
            onCategoryItemClicked.invoke(list[position])
        }

    }
}