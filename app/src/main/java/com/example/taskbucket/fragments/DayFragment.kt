package com.example.taskbucket.fragments

import android.annotation.SuppressLint
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databasefinal.Event
import com.example.taskbucket.R
import com.example.taskbucket.adapters.daygridAdapter
import com.example.taskbucket.adapters.daygridEvent
import com.example.taskbucket.viewmodels.EventViewModel
import kotlinx.android.synthetic.main.fragment_bucket.*
import kotlinx.android.synthetic.main.fragment_day.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import androidx.lifecycle.Observer
import java.time.Year

/**
 * A simple [Fragment] subclass.
 */


private var cal: Calendar = Calendar.getInstance()


class DayFragment() : Fragment() {
    val TAG: String = "DayFragment"
    val test = true

    lateinit var viewModel:EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_day, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        viewModel.getEventsByYear(2020)
        viewModel.currentEvents.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "Events: " + it)
        })


        initDay(view)
        populateDayTable()
        day_prev_button.setOnClickListener {
            prevDay(view)
            populateDayTable()
        }

        day_next_button.setOnClickListener {
            nextDay(view)

        }




    }


    fun populateDayTable() {
        var currentEvents: ArrayList<Event> = ArrayList()
        viewModel.getEventsByDay(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))

        viewModel.currentEvents.observe(viewLifecycleOwner, Observer {
            currentEvents = ArrayList(it)
        })


        recyclerView_day.layoutManager = LinearLayoutManager(requireContext())
        var items = arrayListOf<daygridEvent>()
        var emptyEvents = arrayListOf<Event>()



        for(i in 0..23){
            var dayEvents = arrayListOf<Event>()
            for(event in currentEvents){
                if(i ==10 ){
                    items.add((daygridEvent(i, dayEvents)))
                    Log.d(TAG, "onViewCreated: " + dayEvents.size)
                }else{
                    items.add(daygridEvent(i, emptyEvents))
                }

            }
        }
        recyclerView_day.adapter = daygridAdapter(items,requireActivity())
        recyclerView_day.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

    }

    fun initDay(view: View) {
        val sdf = SimpleDateFormat("MM-dd-yyyy")
        val output = sdf.format(cal.time)
        val tv = view.findViewById<TextView>(R.id.day_day)
        tv.text = output

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

        //val chart = view.findViewById<RelativeLayout>(R.id.day_table)
        //chart.removeAllViews()
    }
}
