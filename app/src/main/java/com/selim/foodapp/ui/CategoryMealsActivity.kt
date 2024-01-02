package com.selim.foodapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.selim.foodapp.adapters.CategoryMealsAdapter
import com.selim.foodapp.databinding.ActivityCategoryMealsBinding
import com.selim.foodapp.model.domain.CategoryMeal
import com.selim.foodapp.ui.fragments.HomeFragment
import com.selim.foodapp.viewModels.CategoryViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter
    private lateinit var categoryName:String
    private lateinit var categoryId:String
    private lateinit var categoryThumb:String
    private lateinit var categoryDescription:String
    private lateinit var viewModel:CategoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =ViewModelProvider(this).get(CategoryViewModel::class.java)
        categoryMealsAdapter = CategoryMealsAdapter()
        getOnCategoryItemClicked()
        viewModel.getMealsByCategory(categoryName)
        getCategoryDetails()

        categoryMealsAdapter.onCategoryItemClicked={
            meal ->
            val intent = Intent(this,MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID,meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME,meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getCategoryDetails() {
        viewModel.categories.observe(this) { meals ->
            categoryMealsAdapter.setCategoryMeals(meals as ArrayList<CategoryMeal>)
            binding.tvCategoryMealsCount.text = meals.size.toString() + " meal"
        }

        binding.recyclerViewCategoryMeals.layoutManager =GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        binding.recyclerViewCategoryMeals.adapter =categoryMealsAdapter
    }

    private fun getOnCategoryItemClicked() {
        val intent =intent
        categoryName =intent.extras?.getString(HomeFragment.CATEGORY_NAME).toString()
        categoryId =intent.extras?.getString(HomeFragment.CATEGORY_ID).toString()
        categoryThumb =intent.extras?.getString(HomeFragment.CATEGORY_THUMB).toString()
        categoryDescription =intent.extras?.getString(HomeFragment.CATEGORY_DESCRIPTION).toString()
    }

}