package edu.example.broders.englishwords.updateword

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UpdateWordViewModel() : ViewModel(){

    private val _eventQuizzSetting = MutableLiveData<Boolean>()

    val eventQuizzSetting : LiveData<Boolean>
        get() = _eventQuizzSetting

    init{
        _eventQuizzSetting.value = false
    }

    fun onPlayQuizz() {
        Log.i("HomeViewModel","OnPlayQuizz clicked")
        _eventQuizzSetting.value = true
    }

    fun onPlayQuizzComplete() {
        _eventQuizzSetting.value = false
    }
}