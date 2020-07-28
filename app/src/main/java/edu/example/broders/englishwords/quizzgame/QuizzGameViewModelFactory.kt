package edu.example.broders.englishwords.quizzgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class QuizzGameViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizzGameViewModel::class.java)) {
            return QuizzGameViewModel() as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}