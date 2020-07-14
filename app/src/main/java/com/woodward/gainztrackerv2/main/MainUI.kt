package com.woodward.gainztrackerv2.main

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentMainUIBinding
import com.woodward.gainztrackerv2.exercisedetails.weights.ExerciseDetailsViewModelFactoryWeights
import com.woodward.gainztrackerv2.exercisedetails.weights.ExerciseDetailsViewModelWeights
import com.woodward.gainztrackerv2.exercisedetails.weights.ExerciseDetailsWeights
import com.woodward.gainztrackerv2.utils.Injection

class MainUI : Fragment() {

    val application: Application = requireNotNull(this.activity).application

    private val mainUIViewModel: MainUIViewModel by lazy {
        ViewModelProvider(this, Injection.provideMainUIViewModelFactory(application)).get(
            MainUIViewModel::class.java
        )
    }

    private val exerciseDetailsViewModelWeights: ExerciseDetailsViewModelWeights by lazy {
        ViewModelProvider(this, Injection.provideExerciseDetailsViewModelFactory(application)).get(
            ExerciseDetailsViewModelWeights::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentMainUIBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main_u_i, container, false)

        /**
         *   Will need editing once ExerciseDetails is set up correctly
         */
        val adapter = MainUIAdapter(MainUIAdapterListener { name, date ->
            Toast.makeText(context, "${name} + ${date}", Toast.LENGTH_LONG).show()
        })


        binding.viewModel = mainUIViewModel

        binding.recyclerViewMainUIExerciseListForDate.adapter = adapter

        binding.lifecycleOwner = this

        binding.floatingActionButtonAddExercise.setOnClickListener { view: View ->
            view.findNavController().navigate(MainUIDirections.actionMainUIToCategoryPage())
        }

        mainUIViewModel.exerciseData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }

    /**
     * Need to change the date in both viewModels
     *
     * Might be an override method once calender Util has been imported
     */
    fun onDateChange() {

    }
}