package com.woodward.gainztrackerv2.exercisedetails.weights

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woodward.gainztrackerv2.database.entity.Category
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import com.woodward.gainztrackerv2.databinding.ListItemCategoryDetailsBinding
import com.woodward.gainztrackerv2.databinding.ListItemExerciseDetailsWeightBinding
import com.woodward.gainztrackerv2.exerciseselection.categoryselection.CategoryAdapter
import com.woodward.gainztrackerv2.exerciseselection.categoryselection.CategoryAdapterListener

class ExerciseDetailsAdapterWeights () {


    class ViewHolder private constructor(val binding: ListItemExerciseDetailsWeightBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: WeightedExerciseData,
            clickListener: CategoryAdapterListener
        ) {
            binding.exercise = item

            /**
             *
             * NEED TO IMPLEMENT CLICK LISTENER
             *
             */

            //binding.clickListener = clickListener
            binding.executePendingBindings() //Optimization technique -> tells data binding to immediately commit all prior data bindings
        }

        //Inflation of the view
        companion object {
            fun viewHolderInflation(parent: ViewGroup): ExerciseDetailsAdapterWeights.ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemExerciseDetailsWeightBinding.inflate(layoutInflater, parent, false)
                return ExerciseDetailsAdapterWeights.ViewHolder(binding)
            }
        }

    }
}