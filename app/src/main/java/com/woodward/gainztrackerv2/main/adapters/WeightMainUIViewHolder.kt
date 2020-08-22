package com.woodward.gainztrackerv2.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import com.woodward.gainztrackerv2.databinding.ListItemMainUiBinding

class WeightMainUIViewHolder private constructor(val binding: ListItemMainUiBinding) :
    RecyclerView.ViewHolder(binding.root) {

    //Explicit for the code editing for the bind-able objects of the view
    fun bind(
        item: WeightedExerciseData,
        clickListener: MainUIAdapterListener
    ) {
        binding.exercise = item
        binding.clickListener = clickListener
        binding.executePendingBindings() //Optimization technique -> tells data binding to immediately commit all prior data bindings
    }

    //Inflation of the view
    companion object {
        fun viewHolderInflation(parent: ViewGroup): WeightMainUIViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemMainUiBinding.inflate(layoutInflater, parent, false)
            return WeightMainUIViewHolder(binding)
        }
    }
}