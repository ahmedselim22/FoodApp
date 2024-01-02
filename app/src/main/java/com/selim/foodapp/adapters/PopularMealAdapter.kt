package com.selim.foodapp.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.selim.foodapp.R
import com.selim.foodapp.databinding.PopularMealItemLayoutBinding
import com.selim.foodapp.model.domain.PopularMeal

class PopularMealAdapter :RecyclerView.Adapter<PopularMealAdapter.PopularMealViewHolder>() {
    private var list =ArrayList<PopularMeal>()
    lateinit var onPopularItemClicked :((PopularMeal)->Unit)
    lateinit var onPopularLongItemClick:((PopularMeal)->Unit)
    @SuppressLint("NotifyDataSetChanged")
    fun setPopularMeals(newList:ArrayList<PopularMeal>){
        this.list =newList
        notifyDataSetChanged()
    }

    class PopularMealViewHolder(itemView: View): ViewHolder(itemView){
        val binding = PopularMealItemLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.popular_meal_item_layout,parent,false)
        return PopularMealViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        val meal = list[position]
        Log.d("here adapter", meal.toString())
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.ivPopularItemImage)
        holder.binding.cardViewPopularMealItem.setOnClickListener {
            onPopularItemClicked.invoke(list[position])
        }
        holder.binding.cardViewPopularMealItem.setOnLongClickListener {
            onPopularLongItemClick.invoke(meal)
            true
        }
    }
}