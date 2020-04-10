package com.example.taskbucket.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.databasefinal.Event
import com.example.databasefinal.EventDatabase
import com.example.databasefinal.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.Month

class EventViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: EventRepository
    val oldEvents: LiveData<List<Event>>
    val currentEvents: LiveData<List<Event>>
    val yearList: LiveData<List<Number>>

    init {
        val eventsDao = EventDatabase.getDatabase(application).eventDao()
        repository = EventRepository(eventsDao)
        oldEvents = repository.oldEvents
        currentEvents = repository.currentEvents
        yearList = repository.yearList
    }
    fun insert(event: Event) = viewModelScope.launch {
        repository.insert(event)
    }
    fun deleteOne(id : Number) = viewModelScope.launch {
        repository.deleteOne(id)
    }

    fun getEventsByYear(year : Number) = viewModelScope.launch {
        repository.getEventsByYear(year)
    }
    fun getEventsByMonth(year : Number, month : Month) = viewModelScope.launch {
        repository.getEventsByMonth(year, month)
    }
    fun getEventsByDay(year : Number, month : Month, day : Number) = viewModelScope.launch {
        repository.getEventsByDay(year, month, day)
    }
    fun getEventsByWeek(year : Number, month : Month, week : Number) = viewModelScope.launch {
        repository.getEventsByWeek(year, month, week)
    }

    fun getOld(year: Number, day: Number, month: Month) = viewModelScope.launch {
        repository.getOld(year, day, month)
    }

    fun deleteOld(year: Number, day: Number, month: Month) = viewModelScope.launch {
        repository.deleteOld(year, day, month)
    }

    fun deleteEventsByYear(year : Number) = viewModelScope.launch {
        repository.deleteEventsByYear(year)
    }
    fun deleteEventsByMonth(year : Number, month : Month) = viewModelScope.launch {
        repository.deleteEventsByMonth(year, month)
    }
    fun deleteEventsByDay(year : Number, month : Month, day : Number) = viewModelScope.launch {
        repository.deleteEventsByDay(year, month, day)
    }
    fun deleteEventsByWeek(year : Number, month : Month, week_number : Number) = viewModelScope.launch {
        repository.deleteEventsByWeek(year, month, week_number)
    }

    fun getEventsNoBucket() {
        repository.getEventsNoBucket()
    }

    fun getEventsByProject() {
        repository.getEventsByProject()
    }

    fun updateEvent(id: Number, year: Int?, month: Month?, day: Int?, week_number: Number?, week_day: DayOfWeek?) {
        repository.updateEvent(id, year, month, day, week_number, week_day)
    }

    fun getYears() {
        repository.getYears()
    }
}