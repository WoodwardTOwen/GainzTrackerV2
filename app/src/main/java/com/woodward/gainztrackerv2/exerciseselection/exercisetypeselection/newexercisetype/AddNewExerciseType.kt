package com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection.newexercisetype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.woodward.gainztrackerv2.databinding.FragmentAddNewExerciseTypeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewExerciseType : Fragment(){

    private var _binding : FragmentAddNewExerciseTypeBinding? = null
    private val binding get() = _binding!!

    private val viewModel : NewExerciseTypeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddNewExerciseTypeBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this


        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}