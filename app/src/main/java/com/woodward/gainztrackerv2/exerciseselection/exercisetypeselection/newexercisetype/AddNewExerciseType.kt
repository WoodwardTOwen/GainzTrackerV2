package com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection.newexercisetype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentAddNewExerciseTypeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewExerciseType : Fragment(){

    private var _binding : FragmentAddNewExerciseTypeBinding? = null
    private val binding get() = _binding!!

    private val viewModel : NewExerciseTypeViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        setUpNavigation()
        setUpSnackBar()
    }

    private fun setUpNavigation() {
        TODO("Not yet implemented")
    }

    fun setUpSnackBar() {
        viewModel.snackBarNullOrBlank.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.InvalidInput),
                    Snackbar.LENGTH_SHORT).show()
                viewModel.doneShowingNullorBlankSnackBar()
            }
        })

        viewModel.snackBarAlreadyExists.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.ExerciseTypeAlreadyExists),
                    Snackbar.LENGTH_SHORT).show()
                viewModel.doneShowingAlreadyExistsSnackBar()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddNewExerciseTypeBinding.inflate(inflater, container, false).apply {
            binding.viewModel = viewModel
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}