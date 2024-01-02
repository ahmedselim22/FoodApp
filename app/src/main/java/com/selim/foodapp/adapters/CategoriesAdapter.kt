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
import com.selim.foodapp.databinding.CategoryItemLayoutBinding
import com.selim.foodapp.model.domain.Category

class CategoriesAdapter :RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    private var list =ArrayList<Category>()
    lateinit var onCategoryItemClicked :((Category)->Unit)
    @SuppressLint("NotifyDataSetChanged")
    fun setCategories(newList:ArrayList<Category>){
        this.list =newList
        notifyDataSetChanged()
    }

    class CategoriesViewHolder(itemView: View): ViewHolder(itemView){
        val binding = CategoryItemLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item_layout,parent,false)
        return CategoriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = list[position]
        Log.d("here adapter", category.toString())
        holder.binding.apply {
            tvCategoryItemName.text = category.strCategory
        }
        Glide.with(holder.itemView).load(category.strCategoryThumb).into(holder.binding.ivCategoryItemImage)
        holder.binding.cardViewCategory.setOnClickListener {
            onCategoryItemClicked.invoke(list[position])
        }

    }
}