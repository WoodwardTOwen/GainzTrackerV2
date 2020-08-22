package com.woodward.gainztrackerv2.main.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.woodward.gainztrackerv2.database.entity.CardiovascularExerciseData

class CardioMainUIAdapter(val clickListener: MainUiAdapterListenerCardio) :
    ListAdapter<CardiovascularExerciseData, CardioMainUIViewHolder>(CardioMainUIAdapterDiffUtil()) {

    /**
     * Called when the recyclerview needs a new view holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardioMainUIViewHolder {
        return CardioMainUIViewHolder.viewHolderInflation(parent)
    }

    /**
     * Displays data at certain position for the recyclerview UI -> should update contents of widget
     */
    override fun onBindViewHolder(holder: CardioMainUIViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    fun getCategoryPosition(position: Int) : CardiovascularExerciseData {
        return getItem(position)
    }
}

/**
 * Maybe incorrect with the params -> essentially for when the user wants to access
 * Data about the exercises that are to do with the exerciseName and date
 *
 * Potential Problem with how the data will be loaded in the main view
 * Have to update every field by date + exerciseName for the sets -> its not a separate piece of info
 */
class MainUiAdapterListenerCardio(val clickListener: (name: String?, date: String?) -> Unit) {
    fun onClick(exercises: CardiovascularExerciseData) =
        clickListener(exercises.exerciseName, exercises.date)
}