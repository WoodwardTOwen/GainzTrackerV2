package com.woodward.gainztrackerv2.exerciseselection.categoryselection

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentCategoryPageBinding
import com.woodward.gainztrackerv2.utils.Injection

class CategoryFragment : Fragment() {

    private lateinit var categoryViewModel : CategoryViewModel
    private lateinit var viewModelFactory: CategoryViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        val binding: FragmentCategoryPageBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_category, container, false)

        initialiseUI()


        binding.categoryViewModel = categoryViewModel

        val adapter = CategoryAdapter(CategoryAdapterListener {
                catID ->
            categoryViewModel.onCategorySelected(catID)
        })

        binding.categoryRecyclerView.adapter = adapter

        binding.lifecycleOwner = this

        categoryViewModel.listOfCategories.observe(viewLifecycleOwner, Observer { categories ->
            categories?.let {
                adapter.submitList(categories)
            }
        })

        categoryViewModel.navigateToExerciseType.observe(viewLifecycleOwner, Observer { cat ->
            cat?.let { this.findNavController().navigate(CategoryPageDirections.actionCategoryPageToExerciseTypePage(cat))
                categoryViewModel.onCategoryToExerciseTypeNavigated()
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.category_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_category -> {
                findNavController().navigate(CategoryPageDirections.actionCategoryPageToAddNewCategory())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initialiseUI() {
        val application = requireNotNull(this.activity).application

        viewModelFactory = Injection.provideCategoryViewModelFactory(application)

        categoryViewModel = ViewModelProvider(this, viewModelFactory).get(
            CategoryViewModel::class.java)
    }
}
