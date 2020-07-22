package com.woodward.gainztrackerv2.exerciseselection.categoryselection.newcategory

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.woodward.gainztrackerv2.database.ExerciseDatabase
import com.woodward.gainztrackerv2.database.entity.Category
import com.woodward.gainztrackerv2.repositories.CategoryRepository
import com.woodward.gainztrackerv2.repositories.ExerciseRepository
import com.woodward.gainztrackerv2.utils.isNullOrWhiteSpace
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NewCategoryViewModel @ViewModelInject constructor (val repository: CategoryRepository): ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Ensures the transition to add a new Category has been completed
     */
    private val _transactionCompleted= MutableLiveData<Boolean>()
    val transactionCompleted : LiveData<Boolean>
        get() = _transactionCompleted

    /**
     * SnackBar to alert the category name already exists within the table
     */
    private val _snackBarEvent = MutableLiveData<Boolean?>()
    val snackBarEvent : LiveData<Boolean?>
        get() = _snackBarEvent

    /**
     * SnackBar to alert the user the input they entered was invalid
     * uses a helper method from [HelperMethods.kt] to ensure correct input
     */
    private val _snackBarWrongInput = MutableLiveData<Boolean?>()
    val snackBarWrongInput : LiveData<Boolean?>
        get() = _snackBarWrongInput

    /**
     * Local Database Interactions with repository
     *
     * Create a new category
     */
    suspend fun insertNewCategory (category: Category) = viewModelScope.launch (Dispatchers.IO) {
        repository.insertNewCategory(category)
    }

    private suspend fun checkIfNameExists(name: String?) : Boolean {
        return repository.checkIfNameExists(name)
    }

    /**
     * OnSubmit of the new Category to the database  -> runs checks to ensure 'dirty data' is not inputted into the database
     *
     * 1) Checks input
     * 2) Check if the name already exists
     * 3) Assumes everything is good and creates new Category to be submitted into the db and marks transaction as complete
     */
    fun onSubmit(name: String) {
        viewModelScope.launch {

            when {
                isNullOrWhiteSpace(name) -> {
                    _snackBarWrongInput.value = true
                }
                checkIfNameExists(name) -> {
                    _snackBarEvent.value = true
                }
                else -> {
                    val newCategory = Category()
                    newCategory.categoryName = name
                    insertNewCategory(newCategory)
                    _transactionCompleted.value = true
                }
            }
        }
    }

    fun completedTransaction() {
        _transactionCompleted.value = null
    }

    fun doneShowingSnackBar() {
        _snackBarEvent.value = null
    }

    fun doneShowingWrongInput() {
        _snackBarWrongInput.value = null
    }
}