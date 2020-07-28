package edu.example.broders.englishwords.endgame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class EndGameViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EndGameViewModel::class.java)) {
            return EndGameViewModel() as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}