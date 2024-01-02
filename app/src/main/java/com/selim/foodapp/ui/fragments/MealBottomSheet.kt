package com.selim.foodapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.selim.foodapp.databinding.FragmentMealBottomSheetBinding
import com.selim.foodapp.model.database.MealDatabase
import com.selim.foodapp.ui.MealActivity
import com.selim.foodapp.viewModels.MainViewModel
import com.selim.foodapp.viewModels.MainViewModelFactory

private const val MEAL_ID = "id"
class MealBottomSheet : BottomSheetDialogFragment() {
    private var mealId: String? = null
    private lateinit var viewModel: MainViewModel
    private lateinit var database: MealDatabase
    private lateinit var factory: MainViewModelFactory
    private lateinit var binding :FragmentMealBottomSheetBinding
    private var mealName :String? =null
    private var mealThumb :String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = MealDatabase.getInstance(activity?.applicationContext!!)
        factory = MainViewModelFactory(database)
        viewModel = ViewModelProvider(this,factory).get(MainViewModel::class.java)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMealBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId.let {
            viewModel.getMealsById(it!!)
        }

            viewModel.mealDetails.observe(viewLifecycleOwner) { meal ->
                mealName = meal.strMeal
                mealThumb = meal.strMealThumb
                Glide.with(binding.root).load(meal.strMealThumb)
                    .into(binding.ivBottomSheetMealImage)
                binding.tvBottomSheetMealName.text = meal.strMeal
                binding.tvBottomSheetMealCategory.text = meal.strCategory
                binding.tvBottomSheetMealCountry.text = meal.strArea
            }

        binding.constraintBottomSheetContainer.setOnClickListener {
            val intent =Intent(activity,MealActivity::class.java)
            intent.apply {
                putExtra(HomeFragment.MEAL_ID,mealId.toString())
                putExtra(HomeFragment.MEAL_NAME,mealName)
                putExtra(HomeFragment.MEAL_THUMB,mealThumb)
            }
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(mealId: String) =
            MealBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, mealId)
                }
            }
    }
}