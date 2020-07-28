package edu.example.broders.englishwords.quizz_settings

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.example.broders.englishwords.database.RepertoireDatabaseDao

class QuizzSettingsViewModelFactory(
        private val dataSource: RepertoireDatabaseDao,
        private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizzSettingsViewModel::class.java)) {
            return QuizzSettingsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}