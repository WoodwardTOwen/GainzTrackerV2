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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NewCategoryViewModel @ViewModelInject constructor (val repository: CategoryRepository): ViewModel() {

    private val viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _transactionCompleted= MutableLiveData<Boolean>()
    val transactionCompleted : LiveData<Boolean>
        get() = _transactionCompleted

    private val _snackBarEvent = MutableLiveData<Boolean?>()
    val snackBarEvent : LiveData<Boolean?>
        get() = _snackBarEvent

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

    fun onSubmit(name: String) {
        viewModelScope.launch {

            if(checkIfNameExists(name)) {
                _snackBarEvent.value = true
            }
            else {
                val newCategory = Category()
                newCategory.categoryName = name
                insertNewCategory(newCategory)
                _transactionCompleted.value = true
            }
        }
    }

    fun completedTransaction() {
        _transactionCompleted.value = null
    }

    fun doneShowingSnackBar() {
        _snackBarEvent.value = null
    }
}