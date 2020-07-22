package com.woodward.gainztrackerv2.exerciseselection.categoryselection.newcategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentAddNewCategoryBinding
import com.woodward.gainztrackerv2.databinding.FragmentCategoryPageBinding
import com.woodward.gainztrackerv2.exerciseselection.categoryselection.CategoryFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewCategory : Fragment() {

    private var _binding: FragmentAddNewCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NewCategoryViewModel by viewModels()
    private lateinit var editText : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddNewCategoryBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        editText = binding.catNameEditText

        binding.submitNewCatButton.setOnClickListener {
            viewModel.onSubmit(editText.text.toString())
        }

        viewModel.snackBarEvent.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.CategoryExistsAlert),
                    Snackbar.LENGTH_SHORT).show()
                viewModel.doneShowingSnackBar()
            }
        })

        viewModel.transactionCompleted.observe(viewLifecycleOwner, Observer {
            success ->
            if(success == true) {
                this.findNavController().navigate(AddNewCategoryDirections.addnewcategoryReturnToCategorySuccess())
                viewModel.completedTransaction()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}