package com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentCategoryPageBinding
import com.woodward.gainztrackerv2.databinding.FragmentExerciseTypePageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseTypeFragment : Fragment() {

    private var _binding: FragmentExerciseTypePageBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ExerciseTypeAdapter

    /**
     * Gets the arguments passed over from the category through navigation args
     */
    private val args : ExerciseTypeFragmentArgs by navArgs()

    private val exerciseTypeViewModel: ExerciseTypeViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        exerciseTypeViewModel.setCatID(args.categoryID)
        setUpAdapter()
        setUpObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentExerciseTypePageBinding.inflate(inflater, container, false).apply {
            viewModel = exerciseTypeViewModel
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    fun setUpAdapter() {
        adapter = ExerciseTypeAdapter(ExerciseTypeAdapterListener { exerciseID ->
            //exerciseTypeViewModel.onCategorySelected(catID)
            exerciseTypeViewModel.setCatID(exerciseID)
        })
        binding.exerciseRecyclerView.adapter = adapter
    }

    fun setUpObservers() {
        exerciseTypeViewModel.exerciseTypeList.observe(viewLifecycleOwner, Observer { exerciseTypes ->
            exerciseTypes?.let {
                adapter.submitList(exerciseTypes)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.new_item_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_item -> {
                findNavController().navigate(ExerciseTypeFragmentDirections.actionExerciseTypePageToAddNewExerciseType(exerciseTypeViewModel.catID.value!!))
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