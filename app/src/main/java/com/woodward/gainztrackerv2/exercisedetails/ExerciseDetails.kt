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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseDetails : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentExerciseDetailsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_exercise_details, container, false)

        val application = requireNotNull(this.activity).application
        // Create an instance of the ViewModel Factory.
        val dataSource = ExerciseRepository
        val viewModelFactory = ExerciseDetailsViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment.
        val sleepTrackerViewModel =
            ViewModelProvider(this, viewModelFactory).get(ExerciseDetailsViewModel::class.java)

        //Assign Binding variable to have communication with the ViewModel
        binding.viewModel = sleepTrackerViewModel

        //Ensuring the binding has the lifecycle authorisation so LiveData can operate correctly
        binding.lifecycleOwner = this
        return binding.root
    }
}