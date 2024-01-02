package com.selim.foodapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.selim.foodapp.R
import com.selim.foodapp.databinding.ActivityMainBinding
import com.selim.foodapp.model.database.MealDatabase
import com.selim.foodapp.viewModels.MainViewModel
import com.selim.foodapp.viewModels.MainViewModelFactory


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by lazy {
        val mealDatabase =MealDatabase.getInstance(this)
        val factory = MainViewModelFactory(mealDatabase)
        ViewModelProvider(this,factory).get(MainViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        val navController =this.findNavController(R.id.host_fragment)
//        NavigationUI.setupActionBarWithNavController(this,navController)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.host_fragment)
//        navController.navigateUp()
//        return true
//    }
}