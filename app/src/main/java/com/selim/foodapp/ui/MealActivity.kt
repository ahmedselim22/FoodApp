package com.selim.foodapp.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.selim.foodapp.databinding.ActivityMealBinding
import com.selim.foodapp.model.database.MealDatabase
import com.selim.foodapp.model.domain.Meal
import com.selim.foodapp.ui.fragments.HomeFragment
import com.selim.foodapp.viewModels.MealViewModel
import com.selim.foodapp.viewModels.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    lateinit var binding: ActivityMealBinding
    private lateinit var mealName :String
    private lateinit var mealId :String
    private lateinit var mealThumb :String
    private lateinit var viewModel: MealViewModel
    private lateinit var youtubeLink:String
    private lateinit var mealToSave: Meal
    private lateinit var database: MealDatabase
    private lateinit var factory: MealViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMealBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        database =MealDatabase.getInstance(this)
        factory = MealViewModelFactory(database)
        viewModel = ViewModelProvider(this,factory).get(MealViewModel::class.java)
        getDataFromIntent()
        initViews()
        loading(true)
        viewModel.getMealbyId(mealId)
        getCurrentMealInfo()
        loading(false)
        onYoutubeImgClicked()
        onAddToFavoriteClicked()
    }

    private fun onAddToFavoriteClicked() {
        progressLoading(true)
        binding.fabRandomMealAddToFavorite.setOnClickListener {
            mealToSave.let {meal->
                viewModel.insertNewMeal(meal)
            }
            progressLoading(false)
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
        }
    }


    private fun onYoutubeImgClicked() {
        binding.ivRandromMealImgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW , Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun getCurrentMealInfo() {
        progressLoading(true)
        viewModel.mealInfo.observe(this) { meal ->
            this.mealToSave = meal
            Log.d("here meal by id ", meal.toString())
            binding.apply {
                tvRandomMealCategory.text = meal!!.strCategory
                tvRandomMealArea.text = meal.strArea
                tvRandomMealDescription.text = meal.strInstructions
            }
            progressLoading(false)
            youtubeLink = meal!!.strYoutube.toString()
        }
    }

    private fun initViews() {
        binding.collabsingToolBarRandomMeal.title = mealName
        Glide.with(applicationContext).load(mealThumb).centerCrop().into(binding.ivRandromMealAppBar)
    }

    private fun getDataFromIntent() {
        val intent = intent
        mealId =intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb =intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loading(isLoading: Boolean){
        if (isLoading){
            binding.apply {
                progressBarRandomMeal.visibility = View.VISIBLE
                tvRandomMealCategory.visibility = View.INVISIBLE
                tvRandomMealArea.visibility = View.INVISIBLE
                tvRandomMealInstructions.visibility = View.INVISIBLE
                fabRandomMealAddToFavorite.visibility =View.INVISIBLE
            }
        }
        else{
            binding.apply {
                progressBarRandomMeal.visibility = View.INVISIBLE
                tvRandomMealCategory.visibility = View.VISIBLE
                tvRandomMealArea.visibility = View.VISIBLE
                tvRandomMealInstructions.visibility = View.VISIBLE
                fabRandomMealAddToFavorite.visibility =View.VISIBLE
            }
        }
    }

    private fun progressLoading(isLoading:Boolean){
        if (isLoading){
            binding.progressBarMealActivity.visibility = View.VISIBLE
            binding.progressBarMealActivity.isFocusable = true
        }
        else{
            binding.progressBarMealActivity.visibility = View.GONE
            binding.progressBarRandomMeal.isFocusable = false
        }
    }
}