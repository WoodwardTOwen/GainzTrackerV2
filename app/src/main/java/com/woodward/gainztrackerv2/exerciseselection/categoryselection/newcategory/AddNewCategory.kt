package com.woodward.gainztrackerv2.exerciseselection.categoryselection.newcategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentAddNewCategoryBinding
import com.woodward.gainztrackerv2.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewCategory : Fragment() {

    private var _binding: FragmentAddNewCategoryBinding? = null
    private val binding get() = _binding!!

    private val newCategoryViewModel: NewCategoryViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        setUpNavigation()
        setUpSnackBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddNewCategoryBinding.inflate(inflater, container, false).apply {
            viewModel = newCategoryViewModel
        }

        return binding.root
    }

    fun setUpNavigation() {
        newCategoryViewModel.transactionCompleted.observe(viewLifecycleOwner, Observer {
                success ->
            if(success == true) {
                this.findNavController().navigate(AddNewCategoryDirections.addnewcategoryReturnToCategorySuccess())
                hideKeyboard()
                newCategoryViewModel.completedTransaction()
            }
        })
    }

    fun setUpSnackBar() {
        newCategoryViewModel.snackBarEvent.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.CategoryExistsAlert),
                    Snackbar.LENGTH_SHORT).show()
                newCategoryViewModel.doneShowingSnackBar()
            }
        })

        newCategoryViewModel.snackBarWrongInput.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.InvalidInput),
                    Snackbar.LENGTH_SHORT).show()
                newCategoryViewModel.doneShowingWrongInput()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}