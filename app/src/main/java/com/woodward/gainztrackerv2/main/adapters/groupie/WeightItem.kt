package com.woodward.gainztrackerv2.main.adapters.groupie

import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import com.woodward.gainztrackerv2.databinding.ListItemMainUiBinding
import com.xwray.groupie.Item
import com.xwray.groupie.databinding.BindableItem
import javax.inject.Inject

class WeightItem constructor(
    private val item: WeightedExerciseData,
    private val listener: MainUIAdapterListener
) : BindableItem<ListItemMainUiBinding>() {

    var weightListener: ((WeightedExerciseData) -> Unit)? = null

    override fun bind(viewBinding: ListItemMainUiBinding, position: Int) {
        viewBinding.exercise = item
        viewBinding.clickListener = listener
        viewBinding.deleteItemInItem.setOnClickListener { weightListener?.invoke(item) }
        viewBinding.executePendingBindings()
    }

    override fun getLayout() = R.layout.list_item_main_ui

    override fun isSameAs(other: Item<*>): Boolean {
        return other is WeightItem
    }

    override fun hasSameContentAs(other: Item<*>): Boolean {
        return other is WeightItem && other.item == item
    }

    fun getWeightExercisePosition(position: Int): Item<*> {
        return getItem(position)
    }
}

class MainUIAdapterListener(val clickListener: (exercise: WeightedExerciseData) -> Unit) {
    fun onClick(exercises: WeightedExerciseData) = clickListener(exercises)
}

