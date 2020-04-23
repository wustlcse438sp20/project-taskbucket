package com.example.taskbucket.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.databasefinal.Event
import com.example.taskbucket.R
import com.example.taskbucket.adapters.MonthAdapter
import com.example.taskbucket.adapters.monthEvent
import com.example.taskbucket.viewmodels.EventViewModel
import kotlinx.android.synthetic.main.fragment_month.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MonthFragment : Fragment() {
    private var dateList: ArrayList<monthEvent> = ArrayList()
    private var cal: Calendar = Calendar.getInstance()
    private lateinit var adapter: MonthAdapter
    private var month = cal.get(Calendar.MONTH)
    private var year = cal.get(Calendar.YEAR)
    lateinit var viewModel: EventViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_month, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.month_recycler_view)
        adapter = MonthAdapter(dateList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this.context, 7)
        viewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        initMonth()

        month_prev_button.setOnClickListener {
            viewModel.currentEvents.removeObservers(viewLifecycleOwner)
            prevMonth(view)
        }

        month_next_button.setOnClickListener {
            viewModel.currentEvents.removeObservers(viewLifecycleOwner)
            nextMonth(view)
        }
    }

    fun initMonth() {
        var sdf = SimpleDateFormat("MMMM-yyyy")

        month_title.text = sdf.format(cal.time)
        sdf = SimpleDateFormat("dd")
        var currentEvents: ArrayList<Event> = ArrayList()
        viewModel.getEventsByMonth(year, month)

        cal.set(Calendar.WEEK_OF_MONTH, 1)
        cal.set(Calendar.DAY_OF_WEEK, 1)


        viewModel.currentEvents.observe(viewLifecycleOwner, Observer {

            currentEvents.addAll(ArrayList(it))
            currentEvents.sortBy { it.day }



            for (i in 0..41) {
                var hasEvent = false
                var inMonth = false
                if (cal.get(Calendar.MONTH) == month) {
                    inMonth = true
                    var dateEvents = currentEvents.filter { event -> event.day == cal.get(Calendar.DATE) }
                    if (dateEvents.isNotEmpty()) {

                        hasEvent = true
                        currentEvents.removeAll(dateEvents)
                    }
                }

                dateList.add(monthEvent(sdf.format(cal.time), hasEvent, inMonth))
                cal.add(Calendar.DAY_OF_YEAR, 1)
            }


            adapter.notifyDataSetChanged()
        })

        cal.set(Calendar.MONTH, month-1)
    }

    fun updateMonth() {
        dateList.clear()
        var sdf = SimpleDateFormat("MMMM-yyyy")

        month_title.text = sdf.format(cal.time)
        sdf = SimpleDateFormat("dd")
        var currentEvents: ArrayList<Event> = ArrayList()
        viewModel.getEventsByMonth(year, month)

        cal.set(Calendar.WEEK_OF_MONTH, 1)
        cal.set(Calendar.DAY_OF_WEEK, 1)


        viewModel.currentEvents.observe(viewLifecycleOwner, Observer {

            currentEvents.addAll(ArrayList(it))
            currentEvents.sortBy { it.day }



            for (i in 0..41) {
                var hasEvent = false
                var inMonth = false
                if (cal.get(Calendar.MONTH) == month) {
                    inMonth = true
                    var dateEvents = currentEvents.filter { event -> event.day == cal.get(Calendar.DATE) }
                    if (dateEvents.isNotEmpty()) {

                        hasEvent = true
                        currentEvents.removeAll(dateEvents)
                    }
                }

                dateList.add(monthEvent(sdf.format(cal.time), hasEvent, inMonth))
                cal.add(Calendar.DAY_OF_YEAR, 1)
            }


            adapter.notifyDataSetChanged()
        })

        viewModel.currentEvents.removeObservers(viewLifecycleOwner)
        cal.set(Calendar.MONTH, month)
    }

    fun prevMonth(view: View) {

//
        if (month >= 11 ) {
            year -= 1
            println("YEAR: " + year)
            cal.set(Calendar.YEAR, year)
        }
        println("Calendar.MONTH: " + month)
        cal.add(Calendar.MONTH, -1)
            month = cal.get(Calendar.MONTH)
        println("Calendar.MONTH: " + month)


        updateMonth()
    }


    fun nextMonth(view: View) {
        if(month == 11){
            cal.set(Calendar.YEAR, year)
            year += 1
        }
        println("Calendar.MONTH: " + month)
        cal.add(Calendar.MONTH, 1)
        month = cal.get(Calendar.MONTH)
        println("Calendar.MONTH: " + month)


        updateMonth()
    }

}
