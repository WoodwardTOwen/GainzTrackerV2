package com.woodward.gainztrackerv2.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.database.dao.WeightedExerciseDao
import com.woodward.gainztrackerv2.databinding.FragmentMainUIBinding
import com.woodward.gainztrackerv2.repository.ExerciseRepository
import com.woodward.gainztrackerv2.utils.Injection

class MainUI : Fragment() {

    private lateinit var mainUIViewModel: MainUIViewModel
    private lateinit var viewModelFactory: MainUIViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentMainUIBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_u_i, container, false)

        val application = requireNotNull(this.activity).application

        viewModelFactory = Injection.provideMainUIViewModelFactory(application)

        mainUIViewModel = ViewModelProvider(this, viewModelFactory).get(MainUIViewModel::class.java)

        binding.viewModel = mainUIViewModel

        binding.lifecycleOwner = this

        binding.navigationButton.setOnClickListener{ view : View ->
            view.findNavController().navigate(R.id.action_mainUI_to_exerciseDetails)
        }

        return binding.root
    }

}