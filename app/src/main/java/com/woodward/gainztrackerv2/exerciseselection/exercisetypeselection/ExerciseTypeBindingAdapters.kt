package com.woodward.gainztrackerv2.exerciseselection.exercisetypeselection

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.woodward.gainztrackerv2.R

@BindingAdapter("exerciseTypesFormatted")
fun TextView.exerciseTypes(item: String?) {
    item?.let {
        this.text = if (item.isNullOrEmpty()) "" else "$item"
    }
}

/**
 * Binding Adapter for the title in the add new exercise type page
 * -> allows user to know which category the exercise type it'll be apart of
 */
@BindingAdapter("exerciseTypeCategoryTitle")
fun TextView.exerciseTypeCategoryTitle(item: String?) {
    item?.let {
        this.text = "${this.context.getString(R.string.SelectedCategory)} ${item}"
    }
}