package com.woodward.gainztrackerv2.main.adapters.groupie

import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.database.entity.CardiovascularExerciseData
import com.woodward.gainztrackerv2.databinding.ListItemMainUiCardioBinding
import com.xwray.groupie.databinding.BindableItem
import javax.inject.Inject

class CardioItem @Inject constructor(private val item: CardiovascularExerciseData) : BindableItem<ListItemMainUiCardioBinding>() {
    override fun bind(viewBinding: ListItemMainUiCardioBinding, position: Int) {
        viewBinding.exercise = item
        viewBinding.executePendingBindings()
    }

    override fun getLayout() = R.layout.list_item_main_ui_cardio

}