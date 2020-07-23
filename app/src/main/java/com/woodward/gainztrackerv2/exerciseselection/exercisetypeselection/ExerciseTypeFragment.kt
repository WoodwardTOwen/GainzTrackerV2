package com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentAddNewCategoryBinding
import com.woodward.gainztrackerv2.databinding.FragmentExerciseTypePageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseTypeFragment : Fragment() {

    private var _binding: FragmentExerciseTypePageBinding? = null
    private val binding get() = _binding!!

    private val exerciseTypeViewModel: ExerciseTypeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentExerciseTypePageBinding.inflate(inflater, container, false)

        val arguments = arguments?.let { ExerciseTypeFragmentArgs.fromBundle(it) }


        return binding.root
    }
}