package com.selim.foodapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.selim.foodapp.adapters.CategoriesAdapter
import com.selim.foodapp.databinding.FragmentCategoriesBinding
import com.selim.foodapp.model.domain.Category
import com.selim.foodapp.ui.CategoryMealsActivity
import com.selim.foodapp.ui.MainActivity
import com.selim.foodapp.viewModels.CategoryViewModel
import com.selim.foodapp.viewModels.MainViewModel

class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var adapter: CategoriesAdapter
    private lateinit var viewModel: MainViewModel
    private lateinit var categoryMealsViewModel:CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        adapter = CategoriesAdapter()
        categoryMealsViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressLoading(true)
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            adapter.setCategories(categories as ArrayList<Category>)
            progressLoading(false)
        }
        binding.recyclerViewCategories.layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        binding.recyclerViewCategories.adapter = adapter
        adapter.onCategoryItemClicked = {
            category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME,category.strCategory)
            intent.putExtra(HomeFragment.CATEGORY_ID,category.idCategory)
            intent.putExtra(HomeFragment.CATEGORY_DESCRIPTION,category.strCategoryDescription)
            intent.putExtra(HomeFragment.CATEGORY_THUMB,category.strCategoryThumb)
            startActivity(intent)
        }
    }

    private fun progressLoading(isLoading:Boolean){
        if (isLoading){
            binding.progressBarCategories.visibility = View.VISIBLE
            binding.progressBarCategories.isFocusable = true
        }
        else{
            binding.progressBarCategories.visibility = View.GONE
            binding.progressBarCategories.isFocusable = false
        }
    }

}