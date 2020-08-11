package com.woodward.gainztrackerv2.main

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.woodward.gainztrackerv2.R

/**
 * For the Main RecyclerView in the Main UI -> Binding adapter for sets
 */

@BindingAdapter("setsFormatted") //Might need re-writing
fun TextView.setsFormatted(item: Int?) {
    item?.let {
        this.text = "${this.context.getString(R.string.Sets)} ${item}"
    }
}

/**
 * For the Main RecyclerView in the Main UI -> Binding adapter for the exercise name
 */

@BindingAdapter("exerciseNameFormatted")
fun TextView.exerciseNameFormatted(item: String?) {
    item?.let {
        this.text = if (item.isNullOrEmpty()) "" else "$item"
    }
}