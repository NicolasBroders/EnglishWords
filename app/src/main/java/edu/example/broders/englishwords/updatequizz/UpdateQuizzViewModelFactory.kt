package edu.example.broders.englishwords.updatequizz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class UpdateQuizzViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateQuizzViewModel::class.java)) {
            return UpdateQuizzViewModel() as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}