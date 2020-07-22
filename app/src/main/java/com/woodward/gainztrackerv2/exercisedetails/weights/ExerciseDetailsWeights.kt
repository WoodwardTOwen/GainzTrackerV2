package com.woodward.gainztrackerv2.exercisedetails.weights

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentExerciseDetailsWeightsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseDetailsWeights : Fragment() {

    private val exerciseDetailsViewModel: ExerciseDetailsViewModelWeights by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentExerciseDetailsWeightsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_exercise_details_weights, container, false)
        //Assign Binding variable to have communication with the ViewModel
        binding.viewModel = exerciseDetailsViewModel
        //Ensuring the binding has the lifecycle authorisation so LiveData can operate correctly
        binding.lifecycleOwner = this
        return binding.root
    }
}