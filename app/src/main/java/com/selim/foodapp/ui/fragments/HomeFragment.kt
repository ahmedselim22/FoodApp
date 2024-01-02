package com.selim.foodapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.selim.foodapp.R
import com.selim.foodapp.adapters.CategoriesAdapter
import com.selim.foodapp.adapters.PopularMealAdapter
import com.selim.foodapp.databinding.FragmentHomeBinding
import com.selim.foodapp.model.domain.Category
import com.selim.foodapp.model.domain.Meal
import com.selim.foodapp.model.domain.PopularMeal
import com.selim.foodapp.ui.CategoryMealsActivity
import com.selim.foodapp.ui.MainActivity
import com.selim.foodapp.ui.MealActivity
import com.selim.foodapp.viewModels.MainViewModel
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: MainViewModel
    private var meal:Meal? =null
    lateinit var popularMealsAdapter:PopularMealAdapter
    lateinit var categoriesAdapter:CategoriesAdapter

    companion object{
        const val MEAL_ID ="mealId"
        const val MEAL_NAME ="mealName"
        const val MEAL_THUMB ="mealThumb"
        const val CATEGORY_ID ="categoryId"
        const val CATEGORY_NAME ="categoryName"
        const val CATEGORY_THUMB ="categoryThumb"
        const val CATEGORY_DESCRIPTION ="categoryDescription"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        popularMealsAdapter = PopularMealAdapter()
        categoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressLoading(true)
        viewModel.randomMeal.observe(viewLifecycleOwner) { meal ->
            binding.tvHomeCardRandomMealName.text = meal.strMeal
            Glide.with(binding.ivHomeCardImgRandomMeal).load(meal.strMealThumb)
                .into(binding.ivHomeCardImgRandomMeal)
            this.meal = meal
            progressLoading(false)
        }
        onRandomMealClicked()

        viewModel.popularMeals.observe(viewLifecycleOwner) { popularMealsList ->
            Log.d("here home popular", popularMealsList.toString())
            popularMealsAdapter.setPopularMeals(popularMealsList as ArrayList<PopularMeal>)
        }
        binding.recyclerHomePopularItems.adapter = popularMealsAdapter
        onPopularItemClicked()

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            Log.d("here home categories", categories.toString())
            categoriesAdapter.setCategories(categories as ArrayList<Category>)
        }
        binding.recyclerHomeCategories.adapter = categoriesAdapter
        binding.recyclerHomeCategories.layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        onCategoryItemClicked()
        onPopularItemLongClicked()

        binding.ivHomeSearchIcon.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mealSearchFragment)
        }
    }

    private fun onPopularItemLongClicked() {
        popularMealsAdapter.onPopularLongItemClick ={
            popularMeal ->
            val bottomSheet =MealBottomSheet.newInstance(popularMeal.idMeal)
            bottomSheet.show(childFragmentManager,"mealInfo")
        }
    }

    private fun onCategoryItemClicked() {
        categoriesAdapter.onCategoryItemClicked={
            category ->
            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            intent.putExtra(CATEGORY_ID,category.idCategory)
            intent.putExtra(CATEGORY_DESCRIPTION,category.strCategoryDescription)
            intent.putExtra(CATEGORY_THUMB,category.strCategoryThumb)
            startActivity(intent)
        }
    }

    private fun onPopularItemClicked() {
        popularMealsAdapter.onPopularItemClicked ={
            meal->
            val intent =Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }


    private fun onRandomMealClicked() {
        binding.cardViewHomeRandomMeal.setOnClickListener {
            val intent =Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal?.idMeal.toString())
            intent.putExtra(MEAL_NAME,meal?.strMeal)
            intent.putExtra(MEAL_THUMB,meal?.strMealThumb)
            startActivity(intent)
        }
    }

    private fun progressLoading(isLoading:Boolean){
        if (isLoading){
            binding.progressBarHome.visibility = View.VISIBLE
            binding.progressBarHome.isFocusable = true
        }
        else{
            binding.progressBarHome.visibility = View.GONE
            binding.progressBarHome.isFocusable = false
        }
    }
}