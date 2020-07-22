package com.woodward.gainztrackerv2.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentMainUIBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainUI : Fragment() {

    private val mainUIViewModel : MainUIViewModel by viewModels()

    /*private val exerciseDetailsViewModelWeights: ExerciseDetailsViewModelWeights by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, Injection.provideExerciseDetailsViewModelFactory(activity.application)).get(
            ExerciseDetailsViewModelWeights::class.java
        )
    }*/

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