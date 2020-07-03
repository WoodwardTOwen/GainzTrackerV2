package com.woodward.gainztrackerv2.exercisedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentExerciseDetailsBinding
import com.woodward.gainztrackerv2.repository.ExerciseRepository

class ExerciseDetails : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentExerciseDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_exercise_details, container, false)

        val application = requireNotNull(this.activity).application
        // Create an instance of the ViewModel Factory.
        val dataSource = ExerciseRepository
        //Instantiates one and only one ViewModel
        val viewModelFactory = ExerciseDetailsViewModelFactory(dataSource, application)
        // Get a reference to the ViewModel associated with this fragment.
        val exerciseDetailsViewModel =
            ViewModelProvider(this, viewModelFactory).get(ExerciseDetailsViewModel::class.java)

        //Assign Binding variable to have communication with the ViewModel
        binding.viewModel = exerciseDetailsViewModel

        //Ensuring the binding has the lifecycle authorisation so LiveData can operate correctly
        binding.lifecycleOwner = this
        return binding.root
    }
}