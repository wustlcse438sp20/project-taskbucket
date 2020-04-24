package com.example.taskbucket.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.view.ActionMode
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.databasefinal.Event
import com.example.databasefinal.Project
import com.example.taskbucket.R
import com.example.taskbucket.adapters.projectAdapter
import com.example.taskbucket.viewmodels.EventViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.sidebar.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Month

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
            bigList[event.month].add(event)
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
            bigList[event.day].add(event)
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
fun dataDividerYear(yearList: ArrayList<Int>, events: ArrayList<Event>) : ArrayList<ArrayList<Event>> {
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
    lateinit var mBottomNav: BottomNavigationView
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var recyclerView: RecyclerView
    lateinit var imageButton: ImageButton
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var sidebarImage: ImageView
    private var eventViewModel: EventViewModel? = null
    private var uniqueYears: ArrayList<Int> = ArrayList()
    private var currentEvents: ArrayList<Event> = ArrayList()
    lateinit var NavigationView: NavigationView
    private var currentProjects: ArrayList<Project> = ArrayList()
    private var lastProjectId : Int = 0
    private var done: Boolean = false
    val TAG: String = "MainActivity"
    val date = LocalDateTime.now()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBottomNav = findViewById(R.id.bottom_nav)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        NavigationView = findViewById(R.id.nav_view)
        recyclerView = findViewById(R.id.sidebar_rv)
        imageButton = findViewById(R.id.sidebar_ib_add)
        //set up bottom nav
        val controller = findNavController(R.id.nav_host_fragment)
        mBottomNav.setupWithNavController(controller)

        //set up navigation drawer
        val appBarConfiguration = AppBarConfiguration(controller.graph, mDrawerLayout)
        val drawerNavController = findNavController(R.id.nav_host_fragment)
        NavigationView.setupWithNavController(drawerNavController)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        mDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        sidebar_ib_add.setOnClickListener {
            controller.navigate(R.id.action_global_addProjectDialogFragment)
        }


//        eventViewModel!!.getYears()
//        eventViewModel!!.getEventsNoBucket()
        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        eventViewModel!!.currentEvents.observe(this, Observer { events ->
            // Update the cached copy of the words in the adapter.
            if(events != null) {
                currentEvents.clear()
                currentEvents.addAll(events)
            }
            //adapter.notifyDataSetChanged()
//            Log.d("current", currentEvents.toString())
//            testForUpdate()
        })
        eventViewModel!!.currentProjects.observe(this, Observer { events ->
            // Update the cached copy of the words in the adapter.
            if(events != null) {
                currentProjects.clear()
                currentProjects.addAll(events)
            }
            //adapter.notifyDataSetChanged()
//            Log.d("current", currentProjects.toString())
//            testForUpdate()
        })
        eventViewModel!!.yearList.observe(this, Observer { events ->
            // Update the cached copy of the words in the adapter.
            if(events != null) {
                uniqueYears.clear()
                uniqueYears.addAll(events)
            }
            //adapter.notifyDataSetChanged()
        })
        eventViewModel!!.lastID.observe(this, Observer { events ->
            // Update the cached copy of the words in the adapter.
            if(events != null) {
                lastProjectId = events
            }
            //adapter.notifyDataSetChanged()
//            Log.d("currentLastID", lastProjectId.toString())
        })
//        eventViewModel!!.getAll()
//        eventViewModel!!.insertProject(Project("hello"))
//        eventViewModel!!.insertProject(Project("goodbye"))
//        eventViewModel!!.getProjects()
//        eventViewModel!!.getLastID()
    }

//    override fun onStart() {
//        super.onStart()
//
//    }
    fun oldEventGetter() { // automatically gets current date and calls get old on the database
        eventViewModel!!.getOld(date.year, date.dayOfMonth, date.month.value)
    }
    fun oldEventModifier() { // always call getter before!
        eventViewModel!!.oldEvents // whatever you want users to be able to do
    }
    fun oldEventClear() {
        eventViewModel!!.deleteOld(date.year, date.dayOfMonth, date.month.value)
    }
    fun insertWithOptionals(
        name: String,
        month: Month? = null,
        day: Int? = null,
        year: Int,
        start: Int? = null,
        end: Int? = null,
        description: String? = null, week_number: Int? = null, project_id: Int? = null) {
        var week_day: DayOfWeek? = null
        var calcDay: Int? = week_number
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
        val e = Event(name, month?.value, day, year, start, end, description, week_day?.value,
            calcDay, project_id)
        eventViewModel!!.insert(e)
        Log.d("theevent", e.toString())
    }

    fun yearBucket(year: Int) {
        eventViewModel!!.getEventsByYear(year)
    }
    fun dayBucket(year : Int, month : Month, day: Int) {
        eventViewModel!!.getEventsByDay(year, month.value, day)
    }
    fun monthBucket(year : Int, month : Month) {
        eventViewModel!!.getEventsByMonth(year, month.value)
    }
    fun weekBucket(year : Int, month : Month, week: Int) {
        eventViewModel!!.getEventsByWeek(year, month.value, week)
    }
    // you can write your own based on the stuff in repository, should give you all the functionality
    // you need to pass to adapter or whatever you are using

    fun updateEvent(event: Event, name: String = "",
                    month: Int? = -1,
                    day: Int? = -1,
                    year: Int = -1,
                    start: Int? = -1,
                    end: Int? = -1,
                    description: String? = "",
                    week_day: Int? = -1,
                    week_number: Int? = -1, // 1 for week 1 (1-7), 2 for week 2 (8-14), etc.
                    project_id: Int? = -1) {
//        Log.d("currentCheck", (year!=-1).toString())
        eventViewModel!!.updateEvent(event.id,
            if(name != "") name else event.name,
            if(month != -1) month else event.month,
            if(day != -1) day else event.day,
            if(year != -1) year else event.year,
            if(start != -1) start else event.start,
            if(end != -1) end else event.end,
            if(description != "") description else event.description,
            if(week_day != -1) week_day else event.week_day,
            if(week_number != -1) week_number else event.week_number,
            if(project_id != -1) project_id else event.project_id)
    }

    fun getLastProjectID() {
        eventViewModel!!.getLastID()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bucket_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

//    fun testForUpdate() {
//        if(!done) {
//            val e = currentEvents[0]
//            Log.d("currentSingleEvent", e.toString())
//            updateEvent(e, name = "Updated!", year = 2001)
//            done = false
//        }
//    }
}
