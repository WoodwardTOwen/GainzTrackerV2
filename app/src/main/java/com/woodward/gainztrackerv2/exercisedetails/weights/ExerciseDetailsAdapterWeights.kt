package com.woodward.gainztrackerv2.exercisedetails.weights

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import com.woodward.gainztrackerv2.databinding.ListItemExerciseDetailsWeightBinding

class ExerciseDetailsAdapterWeights(val clickListener: ExerciseDetailsAdapterListener) :
    ListAdapter<WeightedExerciseData, ExerciseDetailsAdapterWeights.ViewHolder>(
        ExerciseDetailsWeightExerciseDiffUtil()
    ) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.viewHolderInflation(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemExerciseDetailsWeightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: WeightedExerciseData,
            clickListener: ExerciseDetailsAdapterListener
        ) {
            binding.exercise = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        //Inflation of the view
        companion object {
            fun viewHolderInflation(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ListItemExerciseDetailsWeightBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}


class ExerciseDetailsAdapterListener(val clickListener: (weightedExerciseData: WeightedExerciseData) -> Unit) {
    fun onClick(exercises: WeightedExerciseData) =
        clickListener(exercises)
}

class ExerciseDetailsWeightExerciseDiffUtil : DiffUtil.ItemCallback<WeightedExerciseData>() {
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