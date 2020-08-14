package com.woodward.gainztrackerv2.exercisedetails.weights

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import timber.log.Timber

@BindingAdapter("text")
fun setDoubleInTextView(tv: TextView, dbVal: Double?) {

    try {
        //This will occur when view is first created or when the leading zero is omitted
        if (dbVal == null && (tv.text.toString() == "" || tv.text.toString() == ".")) return

        //Validating whats in the text box
        val tvDbl = tv.text.toString().toDouble()
        //If it's the same as what we've just entered then return
        if (tvDbl == dbVal)
            return

        //If val is new, re-assign it to the text view
        tv.text = dbVal?.toString()

    } catch (nfe: java.lang.NumberFormatException) {

        //This is usually caused when tv.text is blank and we've entered the first digit
        tv.text = dbVal?.toString() ?: ""

    }//catch

}//setDoubleInTextView

@InverseBindingAdapter(attribute = "text")
fun getDoubleFromTextView(editText: TextView): Double? {
    return try {
        editText.text.toString().toDouble()
    } catch (e: NumberFormatException) {
        null
    }//catch

}//getDoubleFromTextView

@BindingAdapter("textAttrChanged")
fun setTextChangeListener(editText: TextView, listener: InverseBindingListener) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) = listener.onChange()

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Timber.i("beforeTextChanged $p0")
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Timber.i("onTextChanged $p0")
        }
    })
}


@BindingAdapter("text")
fun setIntInTextView(tv: TextView, intVal: Int?) {

    try {
        //This will occur when view is first created or when the leading zero is omitted
        if (intVal == null && (tv.text.toString() == "" || tv.text.toString() == ".")) return

        //Validating whats in the text box
        val tvDbl = tv.text.toString().toInt()
        //If it's the same as what we've just entered then return
        if (tvDbl == intVal)
            return

        //If val is new, re-assign it to the text view
        tv.text = intVal?.toString()

    } catch (nfe: java.lang.NumberFormatException) {

        //This is usually caused when tv.text is blank and we've entered the first digit
        tv.text = intVal?.toString() ?: ""

    }//catch

}//setDoubleInTextView

@InverseBindingAdapter(attribute = "text")
fun getIntFromTextView(editText: TextView): Int? {
    return try {
        editText.text.toString().toInt()
    } catch (e: NumberFormatException) {
        null
    }//catch

}



/**
 * For the Exercise Details RecyclerView when viewing the exercise detailing (weights) -> Binding adapter for weight (Kg)
 */

@BindingAdapter("weightFormatted")
fun weightFormatted(view: TextView, item: WeightedExerciseData?) {
    item?.let {
        view.text = (item.weight + R.string.Kg).toString()
    }
}

/**
 * For the Exercise Details RecyclerView when viewing the exercise detailing (weights) -> Binding adapter for reps
 */

@BindingAdapter("repsFormatted")
fun repsFormatted(view: TextView, item: WeightedExerciseData?) {
    item?.let {
        view.setText(item.reps + R.string.Reps)
    }
}

/**
 * For the Exercise Details RecyclerView when viewing the exercise detailing (weights) -> Binding adapter for rpe
 */

@BindingAdapter("rpeFormatted")
fun rpeFormatted(view: TextView, item: WeightedExerciseData?) {
    item?.let {
        view.setText(item.rpe + R.string.RPE)
    }
}

@BindingAdapter("categoriesFormatted")
fun TextView.categories(item: String?) {
    item?.let {
        this.text = if (item.isNullOrEmpty()) "" else "$item"
    }
}

@BindingAdapter("dateFormattedExerciseDetails")
fun TextView.exerciseDetailsDate(item: String?) {
    item?.let{
        this.text = "${this.context.getString(R.string.Date)} $item"
    }
}