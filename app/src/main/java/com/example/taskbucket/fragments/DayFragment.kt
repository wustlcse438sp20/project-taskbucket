package com.example.taskbucket.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databasefinal.Event
import com.example.taskbucket.R
import com.example.taskbucket.adapter.DayAdapter
import com.example.taskbucket.adapters.daygridAdapter
import com.example.taskbucket.adapters.daygridEvent
import com.example.taskbucket.data.Events
import kotlinx.android.synthetic.main.fragment_bucket.*
import kotlinx.android.synthetic.main.fragment_day.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */

private var event1 = Events(1, "Lunch6", "aaa", 3, 15, 3, 2020, 720, 765)
private var event2 = Events(2, "Lunch5", "aaea", 3, 15, 3, 2020, 780, 800)
private var event3 = Events(3, "Lunch4", "aasa", 2, 15, 3, 2020, 1200, 1245)
private var event4 = Events(4, "Lunch3", "asaa", 3, 16, 3, 2020, 1200, 1245)
private var event5 = Events(5, "Lunch2", "aahhha", 3, 15, 4, 2020, 1200, 1245)

private var eventList: ArrayList<Events> = ArrayList()
private var cal: Calendar = Calendar.getInstance()
private lateinit var adapter: DayAdapter

class DayFragment() : Fragment() {
    val TAG: String = "DayFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initDay(view)
        eventList.add(event1)
        eventList.add(event2)

        day_prev_button.setOnClickListener {
            prevDay(view)
        }

        day_next_button.setOnClickListener {
            nextDay(view)
        }

        recyclerView_day.layoutManager = LinearLayoutManager(requireContext())
        var items = arrayListOf<daygridEvent>()
        var emptyEvents = arrayListOf<Event>()
        //events.add(Event("test", 3, 4, 2020, 5, 8, "blahblah blash",3,13, 0))
        var event1 = Event("test", 3, 4, 2020, 5, 8, "blahblah blash",3,13, 0)
        var event2 = Event("test", 3, 4, 2020, 5, 8, "blahblah blash",3,13, 0)
        var event3 = Event("test", 3, 4, 2020, 5, 8, "blahblah blash",3,13, 0)
        var event4 = Event("test", 3, 4, 2020, 5, 8, "blahblah blash",3,13, 0)
        var events = arrayListOf<Event>()
        events.add(event1)
        events.add(event2)
        events.add(event3)
        events.add(event4)
        Log.d(TAG, "onViewCreated: " + events.size)
        for(i in 0..23){
            if(i == 10){
                items.add((daygridEvent(i, events)))
            }else{
                items.add(daygridEvent(i, emptyEvents))
            }

        }
        recyclerView_day.adapter = daygridAdapter(items,requireActivity())
        recyclerView_day.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    override fun onResume() {
        super.onResume()

        populateDayTable()
    }

    fun populateDayTable() {
        //val chart = activity!!.findViewById<RelativeLayout>(R.id.day_table)
        //val chart_height = chart.height
        //val chart_width = chart.width

//        println("Height: " + chart_height)
//        println("Width: " + chart_width)
//
//        for(event in eventList){
//            val btn = Button(context)
//            val btn_double:Double = ((event.end_time - event.start_time)/60.0) * (chart_height/24.0)
//            val btn_height:Int = btn_double.toInt()
//            val btn_width:Int = chart_width-(chart_width* 16/100)
//            println("Height: " + btn_height)
//            println("Width: " + btn_width)
//            btn.text = event.title
//            btn.layoutParams = LinearLayout.LayoutParams(btn_width,btn_height)
//            btn.x = chart_width*0.16F
//            btn.y = event.start_time/60F * (chart_height/24)
//            //chart.addView(btn)
//        }
    }

    fun initDay(view: View) {
        val sdf = SimpleDateFormat("MM-dd-yyyy")
        val output = sdf.format(cal.time)
        val tv = view.findViewById<TextView>(R.id.day_day)
        tv.text = output
        println("Year: " + cal.get(Calendar.YEAR))
        println("Month: " +  cal.get(Calendar.MONTH))
        println("Week: " + cal.get(Calendar.WEEK_OF_YEAR))
        println("Day: " + cal.get(Calendar.DAY_OF_WEEK))
    }

    fun prevDay(view: View) {
        val sdf = SimpleDateFormat("MM-dd-yyyy")
        cal.add(Calendar.DATE, -1)
        view.findViewById<TextView>(R.id.day_day).text = sdf.format(cal.time)
        clearEvents(view)

    }

    fun nextDay(view: View) {
        val sdf = SimpleDateFormat("MM-dd-yyyy")
        cal.add(Calendar.DATE, 1)
        view.findViewById<TextView>(R.id.day_day).text = sdf.format(cal.time)
        clearEvents(view)

    }


    fun clearEvents(view:View){
        eventList.clear()
        //val chart = view.findViewById<RelativeLayout>(R.id.day_table)
        //chart.removeAllViews()
    }
}
