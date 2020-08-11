package com.woodward.gainztrackerv2.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentMainUIBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainUI : Fragment() {

    private var _binding: FragmentMainUIBinding? = null
    private val binding get() = _binding!!

    private val mainUIViewModel: MainUIViewModel by viewModels()
    private lateinit var dpd: DatePickerDialog
    private lateinit var adapter: MainUIAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        setUpAdapter()
        setUpNavigation()
        setUpObservers()
        setUpItemTouchHelper()
    }

    private fun setUpItemTouchHelper() {
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    mainUIViewModel.onDeleteList(adapter.getCategoryPosition(viewHolder.adapterPosition))
                    Toast.makeText(
                        context,
                        "Selected Data Deleted for ${mainUIViewModel.currentDate.value}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewMainUIExerciseListForDate)
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
                Toast.makeText(context, "Date Time Picker Launched", Toast.LENGTH_SHORT).show()
                onDisplayDateDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Might be an override method once calender Util has been imported
     */
    private fun onDateChange(year: Int, month: Int, day: Int) {
        mainUIViewModel.setDate("$day/$month/$year")
        mainUIViewModel.setDateTimePickerYear(year)
        mainUIViewModel.setDateTimePickerMonth(month)
        mainUIViewModel.setDateTimePickerDay(day)
        MainActivity.DataHolder.setDate(mainUIViewModel.currentDate.value!!)
    }

    private fun onDisplayDateDialog() {
        dpd = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, yearSelected, monthOfYear, dayOfMonth ->
                binding.textViewMainUITitle.text = "$dayOfMonth/$monthOfYear/$yearSelected"
                onDateChange(yearSelected, monthOfYear, dayOfMonth)
            },
            mainUIViewModel.getDateTimePickerYear(),
            mainUIViewModel.getDateTimePickerMonth(),
            mainUIViewModel.getDateTimePickerDay()
        )
        dpd.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}