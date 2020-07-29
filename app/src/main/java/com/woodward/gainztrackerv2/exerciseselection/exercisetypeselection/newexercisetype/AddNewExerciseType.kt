package com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection.newexercisetype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentAddNewExerciseTypeBinding
import com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection.ExerciseTypeFragmentArgs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddNewExerciseType : Fragment(R.layout.fragment_add_new_exercise_type){

    private var _binding : FragmentAddNewExerciseTypeBinding? = null
    private val binding get() = _binding!!

    private val args : ExerciseTypeFragmentArgs by navArgs()

    private val newExerciseTypeViewModel : NewExerciseTypeViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        setUpObservers()
        newExerciseTypeViewModel.setCatID(args.categoryID)
        //setUpNavigation()
        setUpSnackBar()
    }

    private fun setUpNavigation() {
        TODO("Not yet implemented")
    }

    fun setUpObservers () {
        newExerciseTypeViewModel.storedCatID.observe(viewLifecycleOwner, Observer {
            newExerciseTypeViewModel.getNameForID(it)
        })
    }

    fun setUpSnackBar() {
        newExerciseTypeViewModel.snackBarNullOrBlank.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.InvalidInput),
                    Snackbar.LENGTH_SHORT).show()
                newExerciseTypeViewModel.doneShowingNullorBlankSnackBar()
            }
        })

        binding.isCardioSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            newExerciseTypeViewModel.setCardio(isChecked)
        })

        newExerciseTypeViewModel.snackBarAlreadyExists.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.ExerciseTypeAlreadyExists),
                    Snackbar.LENGTH_SHORT).show()
                newExerciseTypeViewModel.doneShowingAlreadyExistsSnackBar()
            }
        })

        newExerciseTypeViewModel.cardio.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "This is cardio",
                    Snackbar.LENGTH_SHORT).show()
            }
            else if(it == false) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "This is Not Cardio",
                    Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddNewExerciseTypeBinding.inflate(inflater, container, false).apply {
            viewModel = newExerciseTypeViewModel
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}