package com.woodward.gainztrackerv2.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentMainUIBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainUI : Fragment() {

    private var _binding: FragmentMainUIBinding? = null
    private val binding get() = _binding!!

    private val mainUIViewModel: MainUIViewModel by viewModels()

    private lateinit var adapter: MainUIAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        setUpAdapter()
        setUpNavigation()
        setUpObservers()
    }

    private fun setUpObservers() {
        mainUIViewModel.exerciseData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        mainUIViewModel.currentDate.observe(viewLifecycleOwner, Observer {
            MainActivity.DataHolder.setDate(it)
            binding.textViewMainUITitle.text = it
        })
    }

    private fun setUpNavigation() {
        binding.floatingActionButtonAddExercise.setOnClickListener { view: View ->
            view.findNavController().navigate(MainUIDirections.actionMainUIToCategoryPage())
        }
    }

    private fun setUpAdapter() {
        /**
         *   Will need editing once ExerciseDetails is set up correctly
         */
        adapter = MainUIAdapter(MainUIAdapterListener { name, date ->
            Toast.makeText(context, "${name} + ${date}", Toast.LENGTH_LONG).show()
        })

        binding.recyclerViewMainUIExerciseListForDate.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainUIBinding.inflate(inflater, container, false).apply {
            viewModel = mainUIViewModel
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.dateTimPickerMenuItem -> {
                Toast.makeText(context, "HIIIIII, it works", Toast.LENGTH_SHORT).show()
                onDisplayDateDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Might be an override method once calender Util has been imported
     */
    fun onDateChange(year: Int, month: Int, day: Int) {
        mainUIViewModel.setDate("$day/$month/$year")
        MainActivity.DataHolder.setDate(mainUIViewModel.currentDate.value!!)
    }

    fun onDisplayDateDialog () {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, yearSelected, monthOfYear, dayOfMonth ->
            binding.textViewMainUITitle.text = "" + dayOfMonth + "/" + monthOfYear + "/" + yearSelected
            onDateChange(yearSelected, monthOfYear, dayOfMonth)
        }, year, month, day)

        dpd.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}