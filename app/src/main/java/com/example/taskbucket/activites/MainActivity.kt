package com.example.taskbucket.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.databasefinal.Event
import com.example.taskbucket.R
import com.example.taskbucket.viewmodels.EventViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

lateinit var mBottomNav: BottomNavigationView

/**
Divides ArrayList<Event> into 13 buckets

@param events List of events to divide
@return ArrayList<ArrayList<Event>
 */
fun dataDividerMonth(events: ArrayList<Event>): ArrayList<ArrayList<Event>> {
    val bigList = arrayListOf<ArrayList<Event>>()
    for(i in 1 .. 13) {
        bigList[i] = arrayListOf()
    }
    for(event in events) {
        if(event.month != null) {
            bigList[event.month.value].add(event)
        } else {
            bigList[13].add(event)
        }
    }
    return bigList
}
/**
Divides ArrayList<Event> into the number of days in given month + 1 buckets

@param month Value of month enum that all the days are from
@param events list of events to divide
@return ArrayList<ArrayList<Event>
 */
fun dataDividerDay(month: Month, events: ArrayList<Event>): ArrayList<ArrayList<Event>> {
    val bigList = arrayListOf<ArrayList<Event>>()
    for(i in 1 .. (month.minLength() + 1)) {
        bigList[i] = arrayListOf()
    }
    for(event in events) {
        if(event.day != null) {
            bigList[event.day.toInt()].add(event)
        } else {
            bigList[month.minLength()+1].add(event)
        }
    }
    return bigList
}
/**
    Divides ArrayList<Event> into the number of unique years + 1 buckets

    @param yearList list of all unique year values. Use eventViewModel!!.getYears() to update yearlist first
    @param events list of events to divide
    @return ArrayList<ArrayList<Event>
 */
fun dataDividerYear(yearList: ArrayList<Number>, events: ArrayList<Event>) : ArrayList<ArrayList<Event>> {
    val bigList = arrayListOf<ArrayList<Event>>()
    for(i in 1 .. yearList.size) {
        bigList[i] = arrayListOf()
    }
    for (event in events) {
        bigList[yearList.indexOf(event.year)].add(event)
    }
    return bigList
}


class MainActivity : AppCompatActivity() {
    private var eventViewModel: EventViewModel? = null
    private var uniqueYears: ArrayList<Number> = ArrayList()
    private var currentEvents: ArrayList<Event> = ArrayList()
    val date = LocalDateTime.now()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBottomNav = findViewById(
            R.id.bottom_nav
        )
        val controller = findNavController(R.id.nav_host_fragment)
        mBottomNav.setupWithNavController(controller)
        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        eventViewModel!!.currentEvents.observe(this, Observer { events ->
            // Update the cached copy of the words in the adapter.
            currentEvents.clear()
            currentEvents.addAll(events)
            //adapter.notifyDataSetChanged()
        })
        eventViewModel!!.yearList.observe(this, Observer { events ->
            // Update the cached copy of the words in the adapter.
            uniqueYears.clear()
            uniqueYears.addAll(events)
            //adapter.notifyDataSetChanged()
        })
    }
    fun oldEventGetter() { // automatically gets current date and calls get old on the database
        eventViewModel!!.getOld(date.year, date.dayOfMonth, date.month)
    }
    fun oldEventModifier() { // always call getter before!
        eventViewModel!!.oldEvents // whatever you want users to be able to do
    }
    fun oldEventClear() {
        eventViewModel!!.deleteOld(date.year, date.dayOfMonth, date.month)
    }
    fun insertWithOptionals(
        name: String,
        month: Month? = null,
        day: Int? = null,
        year: Int,
        start: Number? = null,
        end: Number? = null,
        description: String? = null, week_number: Number? = null, project_id: Int? = null) {
        var week_day: DayOfWeek? = null
        var calcDay: Number? = week_number
        if(month != null && day != null) {
            val s: String = month.value.toString() + day.toString() + year.toString()
            week_day = LocalDate.parse(s).dayOfWeek
            if(calcDay != null) {
                when (day) {
                    in 1..7 -> calcDay = 1
                    in 8..14 -> calcDay = 2
                    in 15..21 -> calcDay = 3
                    in 22..28 -> calcDay = 4
                    else -> calcDay = 5
                }
            }
        }
        eventViewModel!!.insert(Event(name, month, day, year, start, end, description, week_day,
            calcDay, project_id))
    }

    fun yearBucket(year: Number) {
        eventViewModel!!.getEventsByYear(year)
    }
    fun dayBucket(year : Number, month : Month, day: Number) {
        eventViewModel!!.getEventsByDay(year, month, day)
    }
    fun monthBucket(year : Number, month : Month) {
        eventViewModel!!.getEventsByMonth(year, month)
    }
    fun weekBucket(year : Number, month : Month, week: Number) {
        eventViewModel!!.getEventsByWeek(year, month, week)
    }
    // you can write your own based on the stuff in repository, should give you all the functionality
    // you need to pass to adapter or whatever you are using

    fun updateEvent(id: Number, year: Int? = null, month: Month? = null, day: Int? = null,
                    week_number: Number? = null, week_day: DayOfWeek? = null) {
        eventViewModel!!.updateEvent(id, year, month, day, week_number, week_day)
    }
}
