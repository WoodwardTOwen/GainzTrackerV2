package com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentExerciseTypePageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseTypeFragment : Fragment() {

    private val exerciseTypeViewModel: ExerciseTypeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentExerciseTypePageBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_exercise_type_page,
                container,
                false
            )


        val application = requireNotNull(this.activity).application
        val arguments = arguments?.let { ExerciseTypeFragmentArgs.fromBundle(it) }


        return inflater.inflate(R.layout.fragment_exercise_type_page, container, false)
    }
}