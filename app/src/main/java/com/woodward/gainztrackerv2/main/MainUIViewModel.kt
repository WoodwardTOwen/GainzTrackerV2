package com.woodward.gainztrackerv2.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.woodward.gainztrackerv2.database.entity.WeightedExerciseData
import com.woodward.gainztrackerv2.repositories.ExerciseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*

class MainUIViewModel @ViewModelInject constructor(val repository: ExerciseRepository) :
    ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * year for the datetimePicker
     */

    private val _year = MutableLiveData<Int>()

    /**
     * month for the datetimePicker
     */

    private val _month = MutableLiveData<Int>()

    /**
     * day for the datetimePicker
     */

    private val _day = MutableLiveData<Int>()

    /**
     * For the current time in the user application state
     */
    private val _currentDate = MutableLiveData<String>(initialiseDate())
    val currentDate: LiveData<String>
        get() = _currentDate

    private val _datePickerTriggered = MutableLiveData<Boolean>()
    val datePickerTriggered: LiveData<Boolean>
        get() = _datePickerTriggered


    /**
     * Needs changing also -> manual input currently -> only used for acceptance testing
     */
    private val _navigateToExerciseDetailsWeights = MutableLiveData<String?>()
    val navigateToExerciseData: LiveData<String?>
        get() = _navigateToExerciseDetailsWeights

    val exerciseData: LiveData<List<WeightedExerciseData?>> =
        Transformations.switchMap(currentDate) { currentDate ->
            repository.getNameAndSetsForDateMainUI(currentDate)
        }

    /**
     * Deletes list of data from the database based on specified conditions [name] and [date]
     */

    private suspend fun deleteListOfExerciseData(name: String, date: String) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteListOFWeightExerciseData(name, date)
        }

    /**
     * Date Control Methods
     */

    fun setDate(date: String) {
        _currentDate.value = date
    }

    private fun initialiseDate(): String? {
        val c = Calendar.getInstance()
        setDateTimePickerYear(c.get(Calendar.YEAR))
        setDateTimePickerMonth(c.get(Calendar.MONTH))
        setDateTimePickerDay(c.get(Calendar.DAY_OF_MONTH))
        return "${_day.value}/${_month.value}/${_year.value}"
    }

    /**
     * Methods [onExerciseDetailNavigated] and [onExerciseDetailsClicked] used for handling onClick management
     * within the main recyclerview within the MainUI
     */

    fun setDateTimePickerYear(year: Int) {
        _year.value = year
    }

    fun setDateTimePickerMonth(month: Int) {
        _month.value = month
    }

    fun setDateTimePickerDay(day: Int) {
        _day.value = day
    }

    fun getDateTimePickerYear() = _year.value!!

    fun getDateTimePickerMonth() = _month.value!!

    fun getDateTimePickerDay() = _day.value!!

    private fun onExerciseDetailsClicked(name: String) {
        _navigateToExerciseDetailsWeights.value = name
    }

    private fun onExerciseDetailNavigated() {
        _navigateToExerciseDetailsWeights.value = null
    }

    fun onDeleteList(weightData: WeightedExerciseData) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteListOfExerciseData(weightData.exerciseName!!, weightData.date!!)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}