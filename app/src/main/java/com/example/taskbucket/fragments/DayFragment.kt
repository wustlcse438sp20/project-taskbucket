package com.example.taskbucket.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.example.taskbucket.R
import com.example.taskbucket.adapter.DayAdapter
import com.example.taskbucket.data.Events
import kotlinx.android.synthetic.main.fragment_day.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */


private var event1 = Events(1, "Lunch", "Consuming", 3, 15, 3, 2020, 720, 765)


private var eventList: ArrayList<Events> = ArrayList()
private var cal: Calendar = Calendar.getInstance()
private lateinit var adapter: DayAdapter

class DayFragment() : Fragment() {

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


        day_prev_button.setOnClickListener {
            prevDay(view)
        }

        day_next_button.setOnClickListener {
            nextDay(view)
        }
    }

    override fun onResume() {
        super.onResume()

        populateDayTable()
    }

    fun populateDayTable() {
        val chart = activity!!.findViewById<RelativeLayout>(R.id.day_table)
        val chart_height = chart.height
        val chart_width = chart.width

        println("Height: " + chart_height)
        println("Width: " + chart_width)

        for(event in eventList){
            val btn = Button(context)
            val btn_double:Double = ((event.end_time - event.start_time)/60.0) * (chart_height/24.0)
            val btn_height:Int = btn_double.toInt()
            val btn_width:Int = chart_width-(chart_width* 16/100)
            println("Height: " + btn_height)
            println("Width: " + btn_width)
            btn.text = event.title
            btn.layoutParams = LinearLayout.LayoutParams(btn_width,btn_height)
            btn.x = chart_width*0.16F
            btn.y = event.start_time/60F * (chart_height/24)
            chart.addView(btn)
        }
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
        val chart = view.findViewById<RelativeLayout>(R.id.day_table)
        chart.removeAllViews()
    }
}
