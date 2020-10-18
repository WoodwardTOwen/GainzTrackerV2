package com.woodward.gainztrackerv2.exercisedetails.weights

import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentExerciseDetailsWeightsBinding
import com.woodward.gainztrackerv2.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExerciseDetailsWeights : Fragment() {

    private var _binding: FragmentExerciseDetailsWeightsBinding? = null
    private val binding get() = _binding!!

    private val args: ExerciseDetailsWeightsArgs by navArgs()

    @Inject
    lateinit var adapter: ExerciseDetailsAdapterWeights

    private val exerciseDetailsViewModel: ExerciseDetailsViewModelWeights by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController()

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (exerciseDetailsViewModel.getNewDataSubmittedStatus()!!) {
                navController.navigate(ExerciseDetailsWeightsDirections.actionExerciseDetailsWeightToMainUI())
                exerciseDetailsViewModel.resetStatusForNewDataSubmitted()
            }
            else {
                navController.popBackStack()
            }

        }

        callback.isEnabled

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        exerciseDetailsViewModel.setIsCardio(args.isCardioArg)
        exerciseDetailsViewModel.setExerciseName(args.exerciseName)
        setUpAdapter()
        setUpObservers()
        activity?.title = exerciseDetailsViewModel.exerciseName.value
    }

    private fun setUpObservers() {
        exerciseDetailsViewModel.exerciseData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        exerciseDetailsViewModel.snackBarInvalidInputEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.Input_Error_For_Fields),
                    Snackbar.LENGTH_SHORT
                ).show()
                exerciseDetailsViewModel.doneShowingSnackBarEventInvalidInput()
            }
        })

        exerciseDetailsViewModel.snackBarDeletedDataEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "All Data has Been successfully Deleted for the exercise: ${exerciseDetailsViewModel.exerciseName.value!!} on ${exerciseDetailsViewModel.currentDate.value!!}",
                    Snackbar.LENGTH_SHORT
                ).show()
                exerciseDetailsViewModel.doneShowingSnackBarEventDeletedData()
            }
        })
    }

    private fun setUpAdapter() {
        adapter = ExerciseDetailsAdapterWeights(ExerciseDetailsAdapterListener { weightData ->
            exerciseDetailsViewModel.onClickSetTextBoxData(weightData)
        })

        binding.recyclerViewExerciseDetailsWeight.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            R.id.return_home -> {
                this.findNavController()
                    .navigate(ExerciseDetailsWeightsDirections.actionExerciseDetailsWeightToMainUI())
                true
            }
            R.id.delete_all_exercise_data -> {
                exerciseDetailsViewModel.onDeleteAllData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        activity?.title = getString(R.string.Title)
    }
}