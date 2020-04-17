package com.example.databasefinal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import java.time.DayOfWeek
//import java.time.Int


// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class EventRepository(private val eventDao: EventDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val oldEvents: MutableLiveData<List<Event>> = MutableLiveData()
    val currentEvents: MutableLiveData<List<Event>> = MutableLiveData()
//val currentEvents: MutableLiveData<List<Event>> = eventDao!!.getAllEvents()
    var yearList: MutableLiveData<List<Int>> = MutableLiveData()

//    var lastQuery = "all"
//    var lastYear = 0
//    var lastMonth = 0
//    var lastDay = 0
//    var lastWeek = 0

    fun insert(event: Event) {
//        Log.d("event", event.toString())
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.insert(event)
//            Log.d("eventRow", eventDao!!.insert(event).toString())
        }
    }
    fun getAll() {
//        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.getAllEvents().observeOnce {
                currentEvents.value = it
            }
//        }
    }
    fun deleteOne(id : Int) {
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.deleteOne(id)
        }
    }

    fun getEventsByYear(year : Int) {
//        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.getEventsByYear(year).observeOnce {
                currentEvents.value = it
            }
//        }
    }
    fun getEventsByMonth(year : Int, month : Int) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = eventDao!!.getEventsByMonth(year, month)
//            currentEvents.value = result.value
        eventDao!!.getEventsByMonth(year, month).observeOnce {
            currentEvents.value = it
        }
//        }
    }
    fun getEventsByDay(year : Int, month : Int, day : Int) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = eventDao!!.getEventsByDay(year, month, day)
//            currentEvents.value = result.value
//        }
        eventDao!!.getEventsByDay(year, month, day).observeOnce {
            currentEvents.value = it
        }
    }
    fun getEventsByWeek(year : Int, month : Int, week : Int) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = eventDao!!.getEventsByWeek(year, month, week)
//            currentEvents.value = result.value
//        }
        eventDao!!.getEventsByWeek(year, month, week).observeOnce {
            currentEvents.value = it
        }
    }

    fun getOld(year: Int, day: Int, month: Int) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = eventDao!!.getOld(year, day, month)
//            currentEvents.value = result.value
//        }
        eventDao!!.getOld(year, day, month).observeOnce {
            currentEvents.value = it
        }
    }

    fun getYears() {
//        CoroutineScope(Dispatchers.IO).launch {
        eventDao!!.getAllYears().observeOnce {
            yearList.value = it
        }
//        }
    }

    fun deleteOld(year: Int, day: Int, month: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.deleteOld(year, day, month)
        }
    }

    fun deleteEventsByYear(year : Int) {
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.deleteEventsByYear(year)
        }
    }
    fun deleteEventsByMonth(year : Int, month : Int) {
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.deleteEventsByMonth(year, month)
        }
    }
    fun deleteEventsByDay(year : Int, month : Int, day : Int) {
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.deleteEventsByDay(year, month, day)
        }
    }
    fun deleteEventsByWeek(year : Int, month : Int, week_number : Int) {
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.deleteEventsByWeek(year, month, week_number)
        }
    }

    fun getEventsNoBucket() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = eventDao!!.getEventsNoBucket()
//            currentEvents.value = result.value
//        }
        eventDao!!.getEventsNoBucket().observeOnce {
            currentEvents.value = it
        }
    }

    fun getEventsByProject() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = eventDao!!.getEventsByProject()
//            currentEvents.value = result.value
//        }
        eventDao!!.getEventsByProject().observeOnce {
            currentEvents.value = it
        }
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
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.updateEvent(id, name, month, day, year, start, end, description, week_day, week_number, project_id)
        }
    }

    fun <T> LiveData<T>.observeOnce(observer: (T) -> Unit) { //https://stackoverflow.com/questions/47854598/livedata-remove-observer-after-first-callback
        observeForever(object: Observer<T> {
            override fun onChanged(value: T) {
                removeObserver(this)
                observer(value)
            }
        })
    }
    fun getOneEvent(id: Int){
        eventDao!!.getOneEvent(id).observeOnce {
            currentEvents.value = it
        }
    }

//    fun lastQueryParse() {
//        when(lastQuery) {
//            "all" -> getAll()
//            "year" -> getEventsByYear(lastYear)
//            "month" -> getEventsByMonth(lastYear, lastMonth)
//            "day" -> getEventsByDay(lastYear, lastMonth, lastDay)
//            "week" -> getEventsByWeek(lastYear, lastMonth, lastWeek)
//            "project" -> getEventsByProject()
//            "nobucket" -> getEventsNoBucket()
//        }
//    }

}