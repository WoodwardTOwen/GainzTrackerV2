package com.woodward.gainztrackerv2.main.adapters

import androidx.recyclerview.widget.DiffUtil
import com.woodward.gainztrackerv2.database.entity.CardiovascularExerciseData

class CardioMainUIAdapterDiffUtil : DiffUtil.ItemCallback<CardiovascularExerciseData>() {
    override fun areItemsTheSame(
        oldItem: CardiovascularExerciseData,
        newItem: CardiovascularExerciseData
    ): Boolean {
        return oldItem.cardio_exerciseID == newItem.cardio_exerciseID
    }

    override fun areContentsTheSame(
        oldItem: CardiovascularExerciseData,
        newItem: CardiovascularExerciseData
    ): Boolean {
        return oldItem == newItem
    }

}