package edu.example.broders.englishwords.addquizz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class AddQuizzViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddQuizzViewModel::class.java)) {
            return AddQuizzViewModel() as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}