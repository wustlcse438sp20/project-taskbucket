package com.example.taskbucket.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.databasefinal.Event
import com.example.databasefinal.EventDatabase
import com.example.databasefinal.EventRepository
import com.example.databasefinal.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.DayOfWeek

class EventViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: EventRepository
    val oldEvents: LiveData<List<Event>>
    val currentEvents: LiveData<List<Event>>
    val yearList: LiveData<List<Int>>
    val currentProjects: LiveData<List<Project>>
    val lastID : LiveData<Int>

    init {
        val eventsDao = EventDatabase.getDatabase(application).eventDao()
        repository = EventRepository(eventsDao)
        oldEvents = repository.oldEvents
        currentEvents = repository.currentEvents
        yearList = repository.yearList
        currentProjects = repository.currentProject
        lastID = repository.lastID
    }
    fun insert(event: Event) = viewModelScope.launch {
        repository.insert(event)
    }
    fun deleteOne(id : Int) = viewModelScope.launch {
        repository.deleteOne(id)
    }

    fun getEventsByYear(year : Int)  = viewModelScope.launch {
        repository.getEventsByYear(year)
    }
    fun getEventsByMonth(year : Int, month : Int) = viewModelScope.launch {
        repository.getEventsByMonth(year, month)
    }
    fun getEventsByDay(year : Int, month : Int, day : Int) = viewModelScope.launch {
        repository.getEventsByDay(year, month, day)
    }
    fun getEventsByWeek(year : Int, month : Int, week : Int) = viewModelScope.launch {
        repository.getEventsByWeek(year, month, week)
    }

    fun getOld(year: Int, day: Int, month: Int) = viewModelScope.launch {
        repository.getOld(year, day, month)
    }

    fun deleteOld(year: Int, day: Int, month: Int) = viewModelScope.launch {
        repository.deleteOld(year, day, month)
    }

    fun deleteEventsByYear(year : Int) = viewModelScope.launch {
        repository.deleteEventsByYear(year)
    }
    fun deleteEventsByMonth(year : Int, month : Int) = viewModelScope.launch {
        repository.deleteEventsByMonth(year, month)
    }
    fun deleteEventsByDay(year : Int, month : Int, day : Int) = viewModelScope.launch {
        repository.deleteEventsByDay(year, month, day)
    }
    fun deleteEventsByWeek(year : Int, month : Int, week_number : Int) = viewModelScope.launch {
        repository.deleteEventsByWeek(year, month, week_number)
    }

    fun getEventsNoBucket() {
        repository.getEventsNoBucket()
    }

    fun getEventsByProject() {
        repository.getEventsByProject()
    }

    fun updateEvent(id: Int, name: String,
                    month: Int?,
                    day: Int?,
                    year: Int,
                    start: Int?,
                    end: Int?,
                    description: String?,
                    week_day: Int?,
                    week_number: Int?, // 1 for week 1 (1-7), 2 for week 2 (8-14), etc.
                    project_id: Int?) {
        repository.updateEvent(id, name, month, day, year, start, end, description, week_day, week_number, project_id)
    }

    fun getYears() {
        repository.getYears()
    }

    fun getAll() {
        repository.getAll()
    }

    fun getOneEvent(id: Int){
        repository.getOneEvent(id)
    }

    fun getOneProject(id : Int) {
        repository.getOneProject(id)
    }

    fun getProjects() {
        repository.getProjects()
    }

    fun getLastID() {
        repository.getLastId()
    }

    fun insertProject(project : Project) = viewModelScope.launch {
        repository.insertProject(project)
    }

    fun deleteOneProject(id : Int) {
        repository.deleteOneProject(id)
    }
}