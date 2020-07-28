package edu.example.broders.englishwords.traductor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class TraductorViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TraductorViewModel::class.java)) {
            return TraductorViewModel() as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}