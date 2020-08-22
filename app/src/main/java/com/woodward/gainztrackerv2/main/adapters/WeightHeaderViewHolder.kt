package com.woodward.gainztrackerv2.main.adapters

import androidx.recyclerview.widget.RecyclerView
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.ListItemMainUiWeightHeaderBinding

class WeightHeaderViewHolder(private val binding: ListItemMainUiWeightHeaderBinding, private val onTopicsHeaderListener: ((String) -> Unit)?) : RecyclerView.ViewHolder(binding.root) {

    fun bind() = itemView.run {
        val headerTitle = context.getString(R.string.weight_exercise_data_title)
        binding.titleTextView.text = headerTitle
        setOnClickListener { onTopicsHeaderListener?.invoke(headerTitle) }
    }
}