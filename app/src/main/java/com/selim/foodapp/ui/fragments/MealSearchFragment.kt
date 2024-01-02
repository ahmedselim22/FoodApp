package com.selim.foodapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.selim.foodapp.adapters.SearchedMealsAdapter
import com.selim.foodapp.databinding.FragmentMealSearchBinding
import com.selim.foodapp.model.domain.Meal
import com.selim.foodapp.ui.MainActivity
import com.selim.foodapp.ui.MealActivity
import com.selim.foodapp.viewModels.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MealSearchFragment : Fragment() {
    lateinit var adapter: SearchedMealsAdapter
    lateinit var viewModel :MainViewModel
    private lateinit var binding:FragmentMealSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SearchedMealsAdapter()
        viewModel = ((activity)as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMealSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivSearchedMealsSearchIcon.setOnClickListener {
            val searchQuery =binding.etSearchMealSearchBox.text.toString()
            viewModel.getSearchedMeals(searchQuery)
        }

        var searchJob:Job? = null
        binding.etSearchMealSearchBox.addTextChangedListener {
            searchQuery->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.getSearchedMeals(searchQuery.toString())
            }
        }

        observerSearchedMeals()
        binding.recyclerViewSearchedMeals.adapter = adapter

        adapter.onSearchedMealClicked={
            meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID,meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME,meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerSearchedMeals() {
        viewModel.searchedMeals.observe(viewLifecycleOwner) { meals ->
            Log.d("searched meals search fragment", meals.toString())
            if (meals!=null) {
                adapter.setSearchedMeals(meals as ArrayList<Meal>)
            }
            else{
                Toast.makeText(activity, "No Meal Have That Name !", Toast.LENGTH_SHORT).show()
            }
        }
    }
}