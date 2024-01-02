package com.selim.foodapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.selim.foodapp.adapters.FavoriteMealsAdapter
import com.selim.foodapp.databinding.FragmentFavoritesBinding
import com.selim.foodapp.model.database.MealDatabase
import com.selim.foodapp.ui.MealActivity
import com.selim.foodapp.viewModels.MainViewModel
import com.selim.foodapp.viewModels.MainViewModelFactory

class FavoritesFragment : Fragment() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: FavoriteMealsAdapter
    lateinit var database: MealDatabase
    private lateinit var factory: MainViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database =MealDatabase.getInstance(activity?.applicationContext!!)
        factory = MainViewModelFactory(database)
        viewModel = ViewModelProvider(this,factory)[MainViewModel::class.java]
//        viewModel = (activity as MainActivity).viewModel
        adapter = FavoriteMealsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.favoriteMeals.observe(requireActivity()) { meals ->
            adapter.differ.submitList(meals)
//            adapter.setFavoriteMeals(meals as ArrayList<Meal>)
            Toast.makeText(activity, meals.size.toString(), Toast.LENGTH_SHORT).show()
        }
        binding.recyclerViewFavoriteMeals.adapter = adapter
        binding.recyclerViewFavoriteMeals.layoutManager = GridLayoutManager(activity,2,GridLayoutManager.VERTICAL,false)

            val itemTouchHelper = object:ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT,
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )=true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val currentMeal = adapter.differ.currentList[position]
                viewModel.deleteFavoriteMeal(currentMeal)
                viewModel.getFavoriteMeals()
                Snackbar.make(binding.root,"Meal Deleted",Snackbar.LENGTH_LONG).setAction(
                    "Undo"
                ) {
                    viewModel.insertNewMeal(currentMeal)
                    viewModel.getFavoriteMeals()
                    Toast.makeText(activity, "saved again", Toast.LENGTH_SHORT).show()
                }.show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerViewFavoriteMeals)

        adapter.onFavoriteMealClicked ={
            meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID,meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME,meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }
}

