package com.woodward.gainztrackerv2.exerciseselection.categoryselection.newcategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentAddNewCategoryBinding

class AddNewCategory : Fragment() {

    private val viewModel : NewCategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentAddNewCategoryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_category, container, false)


        return binding.root
    }
}