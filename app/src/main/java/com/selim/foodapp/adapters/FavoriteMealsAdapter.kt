package com.selim.foodapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.selim.foodapp.R
import com.selim.foodapp.databinding.CategoryMealItemLayoutBinding
import com.selim.foodapp.model.domain.Meal



class FavoriteMealsAdapter : RecyclerView.Adapter<FavoriteMealsAdapter.FavoriteMealViewHolder>() {
//    private var list =ArrayList<Meal>()
    lateinit var onFavoriteMealClicked:((Meal)->Unit)
    private val diffUtil = object :DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: Meal, newItem: Meal): Any? {
            return super.getChangePayload(oldItem, newItem)
        }
    }
    val differ = AsyncListDiffer(this,diffUtil)

//    fun setFavoriteMeals(meals:ArrayList<Meal>){
//        this.list = meals
//        notifyDataSetChanged()
//    }
//    fun getFavoriteMealByPosition(position: Int):Meal{
//        return list[position]
//    }

    class FavoriteMealViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = CategoryMealItemLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_meal_item_layout,parent,false)
        return FavoriteMealViewHolder(view)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoriteMealViewHolder, position: Int) {
        val meal = differ.currentList[position]
        holder.binding.apply {
            tvCategoryMealName.text = meal.strMeal
            cardViewCategoryMeal.setOnClickListener {
                onFavoriteMealClicked.invoke(meal)
            }
        }
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.ivCategoryMealImage)

    }
}