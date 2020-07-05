package com.woodward.gainztrackerv2.exercisedetails.weights

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentExerciseDetailsWeightsBinding
import com.woodward.gainztrackerv2.repository.ExerciseRepository
import com.woodward.gainztrackerv2.utils.Injection

class ExerciseDetailsWeights : Fragment() {

    private lateinit var exerciseDetailsViewModel: ExerciseDetailsViewModelWeights
    private lateinit var viewModelFactory: ExerciseDetailsViewModelFactoryWeights

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentExerciseDetailsWeightsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_exercise_details_weights, container, false)
        initialiseUI()
        //Assign Binding variable to have communication with the ViewModel
        binding.viewModel = exerciseDetailsViewModel
        //Ensuring the binding has the lifecycle authorisation so LiveData can operate correctly
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun initialiseUI () {
        val application = requireNotNull(this.activity).application
        //Instantiates one and only one ViewModel
        viewModelFactory = Injection.provideExerciseDetailsViewModelFactory(application)
        // Get a reference to the ViewModel associated with this fragment.
        exerciseDetailsViewModel = ViewModelProvider(this, viewModelFactory).get(
            ExerciseDetailsViewModelWeights::class.java)
    }
}