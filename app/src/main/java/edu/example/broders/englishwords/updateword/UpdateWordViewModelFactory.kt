package edu.example.broders.englishwords.updateword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class UpdateWordViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateWordViewModel::class.java)) {
            return UpdateWordViewModel() as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}