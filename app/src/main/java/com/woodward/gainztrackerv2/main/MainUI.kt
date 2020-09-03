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
import com.google.android.material.snackbar.Snackbar
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import com.woodward.gainztrackerv2.databinding.FragmentMainUIBinding
import com.woodward.gainztrackerv2.main.adapters.groupie.ExpandableHeaderItem
import com.woodward.gainztrackerv2.main.adapters.groupie.MainUIAdapterListener
import com.woodward.gainztrackerv2.main.adapters.groupie.WeightItem
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@AndroidEntryPoint
class MainUI : Fragment() {

    private var _binding: FragmentMainUIBinding? = null
    private val binding get() = _binding!!

    private val mainUIViewModel: MainUIViewModel by viewModels()
    private lateinit var dpd: DatePickerDialog

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val weightSection = Section()
    private val cardioSection = Section()

    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        initRecyclerView()
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
                    //mainUIViewModel.onDeleteList(groupAdapter.getAdapterPosition(viewHolder.bindingAdapterPosition))
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
                updatedItems(it.toWeightItem())
            }
        })

        mainUIViewModel.currentDate.observe(viewLifecycleOwner, Observer {
            MainActivity.DataHolder.setDate(it)
            binding.textViewMainUITitle.text = it
        })
    }

    private fun updatedItems(weightData: List<WeightItem>) {
        weightSection.clear()
        weightSection.addAll(weightData)
        mainUIViewModel.onDateChangeSuccessful()
    }

    fun List<WeightedExerciseData>.toWeightItem(): List<WeightItem> {
        return this.map { exerciseData ->
            createWeightItem(exerciseData)
        }
    }

    private fun createWeightItem(exerciseData: WeightedExerciseData) =
        WeightItem(
            exerciseData,
            createMainUIAdapterListener()
        ).apply {
            weightListener =
                { mainUIViewModel.onDeleteList(it) }
        }

    private fun createMainUIAdapterListener() =
        MainUIAdapterListener { exercise -> exercise.exerciseName?.let { it1 -> showSnackBar(it1) } }

    private fun showSnackBar(message: String) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

    private fun showSnackBarDeletionTemp(message: String) =
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

    private fun initRecyclerView() {

        binding.recyclerViewMainUIExerciseListForDate.apply {
            adapter = groupAdapter.apply {
                setHasStableIds(true)
                if (groupAdapter.groupCount == 0) { // Stops duplication
                    //add(weightSection)
                    this.add(createExpandableHeaderWeights())
                    this.add(createExpandableHeaderCardio())
                }
            }
            setHasFixedSize(true)
        }
    }

    private fun createExpandableHeaderWeights() =
        ExpandableGroup(ExpandableHeaderItem("Weight Exercise Data"), true).apply{
            add(weightSection)
        }

    private fun createExpandableHeaderCardio() =
        ExpandableGroup(ExpandableHeaderItem("CardioVascular Exercise Data"), true).apply{
            add(cardioSection)
        }

    private fun setUpNavigation() {
        binding.floatingActionButtonAddExercise.setOnClickListener { view: View ->
            view.findNavController().navigate(MainUIDirections.actionMainUIToCategoryPage())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
                onDisplayDateDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onDateChange(year: Int, month: Int, day: Int) {
        mainUIViewModel.onDateChange("$day/$month/$year")
        mainUIViewModel.setDate("$day/$month/$year")
        mainUIViewModel.setDateUnit(year, month, day)
        MainActivity.DataHolder.setDate(mainUIViewModel.currentDate.value!!)
    }

    private fun onDisplayDateDialog() {
        dpd = DatePickerDialog(
            requireContext(),
            { _, yearSelected, monthOfYear, dayOfMonth ->
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerViewMainUIExerciseListForDate.adapter = null
    }
}