package com.woodward.gainztrackerv2.main.adapters

import androidx.recyclerview.widget.DiffUtil
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData

class WeightMainUIDiffUtil : DiffUtil.ItemCallback<WeightedExerciseData>() {
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