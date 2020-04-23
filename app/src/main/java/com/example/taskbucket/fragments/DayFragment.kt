package com.example.taskbucket.fragments


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databasefinal.Event
import com.example.taskbucket.R
import com.example.taskbucket.adapters.daygridAdapter
import com.example.taskbucket.adapters.daygridEvent
import com.example.taskbucket.viewmodels.EventViewModel

import kotlinx.android.synthetic.main.fragment_day.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView


/**
 * A simple [Fragment] subclass.
 */


private var cal: Calendar = Calendar.getInstance()


class DayFragment() : Fragment() {
    val TAG: String = "DayFragment"
    val test = true

    lateinit var viewModel:EventViewModel
    lateinit var adapter: daygridAdapter
    private var items = arrayListOf<daygridEvent>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_day, container, false)
        val recyclerView_day = root.findViewById<RecyclerView>(R.id.recyclerView_day)
        adapter = daygridAdapter(items,requireActivity(), this)
        recyclerView_day.adapter = adapter
        recyclerView_day.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_day.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        viewModel.getEventsByYear(2020)
        viewModel.currentEvents.observe(viewLifecycleOwner, Observer {
            println("TEST: " + it)
        })
        initDay(view)

        day_prev_button.setOnClickListener {
            prevDay(view)
            populateDayTable()
        }

        day_next_button.setOnClickListener {
            nextDay(view)
            populateDayTable()
        }




    }


    override fun onResume() {
        super.onResume()
        populateDayTable()
    }

    fun populateDayTable() {
        var currentEvents: ArrayList<Event> = ArrayList()
        viewModel.getEventsByDay(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))

        viewModel.currentEvents.observe(viewLifecycleOwner, Observer {
            items.clear()
            currentEvents = ArrayList(it)
            currentEvents.sortBy { it.start }
            currentEvents = currentEvents.filter { it.start.toString() != "null" } as ArrayList<Event>




            var emptyEvents = arrayListOf<Event>()

            var dayEvents = arrayListOf<Event>()

            var currentHour = 0
            var dayEventMap =  mutableMapOf<Int, ArrayList<Event>>()
            for(i in 0 until currentEvents.size){
                val event = currentEvents[i]
                if(currentEvents.size == 1){
                    currentHour = event.start!! /60
                    dayEvents.add(event)
                    dayEventMap[currentHour] = dayEvents

                }
                else {
                    if(event.start!! / 60 == currentHour){
                        dayEvents.add(event)
                    }
                    else{
                        if(dayEvents.isNotEmpty()){
                            dayEventMap[currentHour] = dayEvents
                        }

                        dayEvents.clear()
                        currentHour = event.start!! / 60
                        dayEvents.add(event)
                        if(i == currentEvents.size-1){
                            dayEventMap[currentHour] = dayEvents
                        }
                    }
                }
            }


            for(i in 0..23){
                if(dayEventMap.containsKey(i)){

                    items.add((daygridEvent(i, dayEventMap.get(i)!!)))
                }else{
                    items.add(daygridEvent(i, emptyEvents))
                }

            }

            adapter.notifyDataSetChanged()

        })




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


    }

    fun nextDay(view: View) {
        val sdf = SimpleDateFormat("MM-dd-yyyy")
        cal.add(Calendar.DATE, 1)
        view.findViewById<TextView>(R.id.day_day).text = sdf.format(cal.time)


    }



}
