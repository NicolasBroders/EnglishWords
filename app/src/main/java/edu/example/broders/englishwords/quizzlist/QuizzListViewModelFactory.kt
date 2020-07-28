package edu.example.broders.englishwords.quizzlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class QuizzListModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizzListViewModel::class.java)) {
            return QuizzListViewModel() as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}