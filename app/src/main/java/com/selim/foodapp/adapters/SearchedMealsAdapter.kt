package com.selim.foodapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.selim.foodapp.R
import com.selim.foodapp.databinding.SearchMealItemLayoutBinding
import com.selim.foodapp.model.domain.Meal



class SearchedMealsAdapter : RecyclerView.Adapter<SearchedMealsAdapter.SearchedMealsViewHolder>() {
    private var list =ArrayList<Meal>()
    lateinit var onSearchedMealClicked:((Meal)->Unit)

    @SuppressLint("NotifyDataSetChanged")
    fun setSearchedMeals(meals:ArrayList<Meal>){
        this.list = meals
        notifyDataSetChanged()
    }


    class SearchedMealsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = SearchMealItemLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedMealsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_meal_item_layout,parent,false)
        return SearchedMealsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SearchedMealsViewHolder, position: Int) {
        val meal = list[position]
        holder.binding.apply {
            tvSearchItemName.text =meal.strMeal
            cardViewSearchMealItem.setOnClickListener {
                onSearchedMealClicked.invoke(meal)
            }
        }
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.ivSearchItemImage)

    }
}