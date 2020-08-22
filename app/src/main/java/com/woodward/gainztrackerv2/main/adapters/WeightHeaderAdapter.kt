package com.woodward.gainztrackerv2.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woodward.gainztrackerv2.databinding.ListItemMainUiWeightHeaderBinding

class WeightHeaderAdapter : RecyclerView.Adapter<WeightHeaderViewHolder>() {

    var onWeightHeaderListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        WeightHeaderViewHolder(ListItemMainUiWeightHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onWeightHeaderListener)

    override fun onBindViewHolder(holderTopics: WeightHeaderViewHolder, position: Int) {
        holderTopics.bind()
    }

    override fun getItemCount() = HEADER

    fun onClear() {
        onWeightHeaderListener = null
    }

    companion object {
        private const val HEADER = 1
    }
}