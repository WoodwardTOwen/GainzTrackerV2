package com.woodward.gainztrackerv2.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woodward.gainztrackerv2.database.entity.CardiovascularExerciseData
import com.woodward.gainztrackerv2.databinding.ListItemMainUiCardioBinding

class CardioMainUIViewHolder private constructor(val binding: ListItemMainUiCardioBinding) :
    RecyclerView.ViewHolder(binding.root) {

    //Explicit for the code editing for the bind-able objects of the view
    fun bind(
        item: CardiovascularExerciseData,
        clickListener: MainUiAdapterListenerCardio
    ) {
        binding.exercise = item
        binding.clickListener = clickListener
        binding.executePendingBindings() //Optimization technique -> tells data binding to immediately commit all prior data bindings
    }

    //Inflation of the view
    companion object {
        fun viewHolderInflation(parent: ViewGroup): CardioMainUIViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemMainUiCardioBinding.inflate(layoutInflater, parent, false)
            return CardioMainUIViewHolder(binding)
        }
    }
}