package edu.example.broders.englishwords.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel() : ViewModel(){

    private val _eventQuizzSetting = MutableLiveData<Boolean>()

    val eventQuizzSetting : LiveData<Boolean>
        get() = _eventQuizzSetting

    private val _eventTraductor = MutableLiveData<Boolean>()

    val eventTraductor : LiveData<Boolean>
        get() = _eventTraductor

    private val _eventQuizzList = MutableLiveData<Boolean>()

    val eventQuizzList : LiveData<Boolean>
        get() = _eventQuizzList

    private val _eventRepository = MutableLiveData<Boolean>()

    val eventRepository : LiveData<Boolean>
        get() = _eventRepository

    init{
        _eventQuizzSetting.value = false
        _eventQuizzList.value = false
        _eventRepository.value = false
        _eventTraductor.value = false
    }

    fun onPlayQuizz() {
        _eventQuizzSetting.value = true
    }

    fun onPlayQuizzComplete() {
        _eventQuizzSetting.value = false
    }

    fun onAccessToTraductor() {
        _eventTraductor.value = true
    }

    fun onAccessToTraductorComplete() {
        _eventTraductor.value = false
    }

    fun onAccessToRepository() {
        _eventRepository.value = true
    }

    fun onAccessToRepositoryComplete() {
        _eventRepository.value = false
    }

    fun onAccessToQuizzList() {
        _eventQuizzList.value = true
    }

    fun onAccessToQuizzListComplete() {
        _eventQuizzList.value = false
    }
}