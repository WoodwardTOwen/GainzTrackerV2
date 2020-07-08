package com.woodward.gainztrackerv2.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import com.woodward.gainztrackerv2.databinding.ListItemMainUiBinding

class MainUIAdapter(/*val clickListener: MainUIAdapterListener*/) :
    ListAdapter<WeightedExerciseData, MainUIAdapter.ViewHolder>(WeightExerciseDiffUtil()) {


    /**
     * Called when the recyclerview needs a new view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.viewHolderInflation(parent)
    }

    /**
     * Displays data at certain position for the recyclerview UI -> should update contents of widget
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class ViewHolder private constructor(val binding: ListItemMainUiBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //Explicit for the code editing for the bind-able objects of the view
        fun bind(item: WeightedExerciseData) {
            binding.exercise = item
            binding.executePendingBindings() //Optimization technique -> tells data binding to immediately commit all prior data bindings
        }

        //Inflation of the view
        companion object {
            fun viewHolderInflation(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemMainUiBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

/**
 * Maybe incorrect with the params -> essentially for when the user wants to access
 * Data about the exercises that are to do with the exerciseName and date
 *
 * Potential Problem with how the data will be loaded in the main view
 * Have to update every field by date + exerciseName for the sets -> its not a separate piece of info
 */
class MainUIAdapterListener(val clickListener: (name: String?, date: String?) -> Unit) {
    fun onClick(exercises: WeightedExerciseData) =
        clickListener(exercises.exerciseName, exercises.date)
}

class WeightExerciseDiffUtil() : DiffUtil.ItemCallback<WeightedExerciseData>() {
    override fun areItemsTheSame(
        oldItem: WeightedExerciseData,
        newItem: WeightedExerciseData
    ): Boolean {
        return oldItem.exerciseId == newItem.exerciseId
    }

    override fun areContentsTheSame(
        oldItem: WeightedExerciseData,
        newItem: WeightedExerciseData
    ): Boolean {
        return oldItem == newItem
    }

}