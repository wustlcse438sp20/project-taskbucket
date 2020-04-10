package com.example.databasefinal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import java.time.DayOfWeek
import java.time.Month


// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class EventRepository(private val eventDao: EventDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val oldEvents: LiveData<List<Event>> = MutableLiveData()
    var currentEvents: LiveData<List<Event>> = MutableLiveData()
    var yearList: LiveData<List<Number>> = MutableLiveData()

    fun insert(event: Event) {
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.insert(event)
        }
    }
    fun deleteOne(id : Number) {
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.deleteOne(id)
        }
    }

    fun getEventsByYear(year : Number) {
        CoroutineScope(Dispatchers.IO).launch {
            currentEvents = eventDao!!.getEventsByYear(year)
        }
    }
    fun getEventsByMonth(year : Number, month : Month) {
        CoroutineScope(Dispatchers.IO).launch {
            currentEvents = eventDao!!.getEventsByMonth(year, month)
        }
    }
    fun getEventsByDay(year : Number, month : Month, day : Number) {
        CoroutineScope(Dispatchers.IO).launch {
            currentEvents = eventDao!!.getEventsByDay(year, month, day)
        }
    }
    fun getEventsByWeek(year : Number, month : Month, week : Number) {
        CoroutineScope(Dispatchers.IO).launch {
            currentEvents = eventDao!!.getEventsByWeek(year, month, week)
        }
    }

    fun getOld(year: Number, day: Number, month: Month) {
        CoroutineScope(Dispatchers.IO).launch {
            currentEvents = eventDao!!.getOld(year, day, month)
        }
    }

    fun getYears() {
        CoroutineScope(Dispatchers.IO).launch {
            yearList = eventDao!!.getAllYears()
        }
    }

    fun deleteOld(year: Number, day: Number, month: Month) {
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.deleteOld(year, day, month)
        }
    }

    fun deleteEventsByYear(year : Number) {
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.deleteEventsByYear(year)
        }
    }
    fun deleteEventsByMonth(year : Number, month : Month) {
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.deleteEventsByMonth(year, month)
        }
    }
    fun deleteEventsByDay(year : Number, month : Month, day : Number) {
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.deleteEventsByDay(year, month, day)
        }
    }
    fun deleteEventsByWeek(year : Number, month : Month, week_number : Number) {
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.deleteEventsByWeek(year, month, week_number)
        }
    }

    fun getEventsNoBucket() {
        CoroutineScope(Dispatchers.IO).launch {
            currentEvents = eventDao!!.getEventsNoBucket()
        }
    }

    fun getEventsByProject() {
        CoroutineScope(Dispatchers.IO).launch {
            currentEvents = eventDao!!.getEventsByProject()
        }
    }

    fun updateEvent(id: Number, year: Int?, month: Month?, day: Int?, week_number: Number?, week_day: DayOfWeek?) {
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.updateEvent(id, year, month, day, week_number, week_day)
        }
    }

}