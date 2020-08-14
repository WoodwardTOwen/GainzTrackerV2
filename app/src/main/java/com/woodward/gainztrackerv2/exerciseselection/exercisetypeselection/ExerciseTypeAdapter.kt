package com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woodward.gainztrackerv2.database.entity.ExerciseType
import com.woodward.gainztrackerv2.databinding.ListItemExerciseTypeDetailsBinding

class ExerciseTypeAdapter (val clickListener : ExerciseTypeAdapterListener) : ListAdapter<ExerciseType, ExerciseTypeAdapter.ViewHolder>(ExerciseTypeDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.viewHolderInflation(parent)
    }

    fun getExerciseTypePosition(position: Int) : ExerciseType {
        return getItem(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!, clickListener)
    }

    class ViewHolder private constructor(val binding: ListItemExerciseTypeDetailsBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ExerciseType, clickListener: ExerciseTypeAdapterListener) {
            binding.exerciseType = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun viewHolderInflation(parent: ViewGroup) : ViewHolder {
                val layoutInflater =  LayoutInflater.from(parent.context)
                val binding = ListItemExerciseTypeDetailsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class ExerciseTypeAdapterListener(val clickListener: (exerciseType : ExerciseType) -> Unit) {
    fun onClick(exercise: ExerciseType) = clickListener(exercise)
}

class ExerciseTypeDiffUtil : DiffUtil.ItemCallback<ExerciseType>() {
    override fun areItemsTheSame(
        oldItem: ExerciseType,
        newItem: ExerciseType
    ): Boolean {
        return oldItem.exerciseTypeID == newItem.exerciseTypeID
    }

    override fun areContentsTheSame(
        oldItem: ExerciseType,
        newItem: ExerciseType
    ): Boolean {
        return oldItem == newItem
    }

}