package com.example.taskbucket.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.databasefinal.Event

data class filterClass(val title: String)
class bucketViewmodel(Application: Application): AndroidViewModel(Application){
    val oldEvents: LiveData<List<Event>>
    val currentEvents: LiveData<List<Event>>
    val yearList: LiveData<List<Int>>

    init {
        oldEvents =  MutableLiveData()
        currentEvents = MutableLiveData()
        yearList = MutableLiveData()
    }
}