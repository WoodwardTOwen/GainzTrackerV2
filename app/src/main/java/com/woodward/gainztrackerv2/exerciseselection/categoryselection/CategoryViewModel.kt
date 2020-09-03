package com.woodward.gainztrackerv2.exerciseselection.categoryselection

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.woodward.gainztrackerv2.database.entity.Category
import com.woodward.gainztrackerv2.repositories.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*

class CategoryViewModel @ViewModelInject constructor(val repository: CategoryRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val listOfCategories = repository.getCategoriesList()

    /**
     * Might need to place a mutableList of Live Data for the [listOfCategories] variable
     * because unsure as to whether the variable will receive updates via New Cat's, Updating and Deletion
     */

    private val _navigateToExerciseType= MutableLiveData<Int?>()
    val navigateToExerciseType :LiveData<Int?>
        get() = _navigateToExerciseType

    private val _currentDate = MutableLiveData<Date>()
    val currentDate : LiveData<Date>
        get() = _currentDate

    /**
     * Current id being set to identify the exercises to be chosen
     */
    fun onCategorySelected(id : Int) {
        _navigateToExerciseType.value = id
    }

    fun onCategoryToExerciseTypeNavigated() {
        _navigateToExerciseType.value = null
    }

    fun onDelete(category: Category) {
        viewModelScope.launch (Dispatchers.IO) {
            deleteCategory(category)
        }
    }

    fun setCurrentDate(date: Date) {
        _currentDate.value = date
    }


    /**
     * Delete a category from a pre-existing list
     */
    private suspend fun deleteCategory(category: Category) {
        repository.deleteCategory(category)
    }

    /**
     * Retrieve all available categories from the local database
     */
    fun getAllCategories() : LiveData<List<Category?>> {
        return listOfCategories
    }

    /**
     * Monitors if the TextView should be displayed over in the view
     */

    val noCategoriesAvailable = Transformations.map(listOfCategories) {
        it.isEmpty()
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}