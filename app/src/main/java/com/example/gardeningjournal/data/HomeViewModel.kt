package com.example.gardeningjournal.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gardeningjournal.database.PlantEntity

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val appContext = application.applicationContext

    private var _message: MutableLiveData<String>? = null
    fun getStringFromResources(resId: Int) {
        val message = appContext.getString(resId)
        println(message)
        _message = MutableLiveData(message)
    }
    val message: LiveData<String>?
        get() = _message;
}