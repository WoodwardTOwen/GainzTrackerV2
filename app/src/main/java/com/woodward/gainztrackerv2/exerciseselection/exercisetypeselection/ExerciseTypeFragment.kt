package com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentExerciseTypePageBinding

class ExerciseTypeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentExerciseTypePageBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_exercise_type_page, container, false)

        val arguments = arguments?.let { ExerciseTypePageArgs.fromBundle(it) }


        return inflater.inflate(R.layout.fragment_exercise_type_page, container, false)
    }
}