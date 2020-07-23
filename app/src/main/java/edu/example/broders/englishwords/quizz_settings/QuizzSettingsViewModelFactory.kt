package edu.example.broders.englishwords.quizz_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class QuizzSettingsViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizzSettingsViewModel::class.java)) {
            return QuizzSettingsViewModel() as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}