package com.woodward.gainztrackerv2.exercisedetails.weights

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentExerciseDetailsWeightsBinding
import com.woodward.gainztrackerv2.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseDetailsWeights : Fragment() {

    private var _binding : FragmentExerciseDetailsWeightsBinding? = null
    private val binding get() = _binding!!

    private val exerciseDetailsViewModel: ExerciseDetailsViewModelWeights by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentExerciseDetailsWeightsBinding.inflate(inflater, container, false).apply {
            //Assign Binding variable to have communication with the ViewModel
            viewModel = exerciseDetailsViewModel
        }
        exerciseDetailsViewModel.setDate(MainActivity.DataHolder.get_Date())
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}