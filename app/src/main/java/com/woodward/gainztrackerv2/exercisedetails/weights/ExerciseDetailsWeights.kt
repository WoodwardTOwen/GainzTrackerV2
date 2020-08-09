package com.woodward.gainztrackerv2.exercisedetails.weights

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentExerciseDetailsWeightsBinding
import com.woodward.gainztrackerv2.exerciseselection.categoryselection.CategoryFragmentDirections
import com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection.ExerciseTypeFragmentArgs
import com.woodward.gainztrackerv2.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseDetailsWeights : Fragment() {

    private var _binding : FragmentExerciseDetailsWeightsBinding? = null
    private val binding get() = _binding!!

    private val args: ExerciseDetailsWeightsArgs by navArgs()

    private val exerciseDetailsViewModel: ExerciseDetailsViewModelWeights by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        exerciseDetailsViewModel.setIsCardio(args.isCardioArg)
        exerciseDetailsViewModel.setExerciseName(args.exerciseName)

        val y = exerciseDetailsViewModel.exerciseName.value!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentExerciseDetailsWeightsBinding.inflate(inflater, container, false).apply {
            //Assign Binding variable to have communication with the ViewModel
            viewModel = exerciseDetailsViewModel
        }
        exerciseDetailsViewModel.setDate(MainActivity.DataHolder.getDate())
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.new_exercise_data_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_exercise_data -> {
                /**
                 * onSubmit Here from the viewModel
                 */
                exerciseDetailsViewModel.onSubmit()


                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}