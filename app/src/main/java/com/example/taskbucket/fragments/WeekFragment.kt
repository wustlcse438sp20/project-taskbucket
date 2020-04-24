package com.example.taskbucket.fragments


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.databasefinal.Event
import com.example.taskbucket.R
import com.example.taskbucket.adapters.weekgridAdapter
import com.example.taskbucket.adapters.weekgridEvent
import com.example.taskbucket.viewmodels.EventViewModel
import kotlinx.android.synthetic.main.fragment_week.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */


private var eventList: ArrayList<String> = ArrayList()
private var cal: Calendar = Calendar.getInstance()

var firstDate = 1

class WeekFragment : Fragment() {

    lateinit var viewModel: EventViewModel
    lateinit var adapter: weekgridAdapter
    private var items = ArrayList<ArrayList<weekgridEvent>>(24)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_week, container, false)
        val week_recycler_view = root.findViewById<RecyclerView>(R.id.week_recycler_view)
        adapter = weekgridAdapter(items, requireActivity(), this)
        week_recycler_view.adapter = adapter
        week_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        week_recycler_view.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        initWeek(view)

        week_prev_button.setOnClickListener {
            prevWeek(view)
        }

        week_next_button.setOnClickListener {
            nextWeek(view)
        }

    }

    private fun populateWeekCalendar() {
        cal.set(Calendar.DAY_OF_YEAR, firstDate)
        cal.add(Calendar.DAY_OF_YEAR, +1)
        var cal2: Calendar = Calendar.getInstance()
        cal2.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE))
        cal.add(Calendar.DAY_OF_YEAR, +1)
        var cal3: Calendar = Calendar.getInstance()
        cal3.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE))
        cal.set(Calendar.DAY_OF_YEAR, firstDate)

        println("Cal1: " + cal.get(Calendar.DAY_OF_MONTH))
        println("Cal2: " + cal2.get(Calendar.DAY_OF_MONTH))
        println("Cal3: " + cal3.get(Calendar.DAY_OF_MONTH))

        var currentEvents: ArrayList<Event> = ArrayList()
        viewModel.getEventsByThreeDay(
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH),
            cal2.get(Calendar.YEAR),
            cal2.get(Calendar.MONTH),
            cal2.get(Calendar.DAY_OF_MONTH),
            cal3.get(Calendar.YEAR),
            cal3.get(Calendar.MONTH),
            cal3.get(Calendar.DAY_OF_MONTH)
        )




        viewModel.currentEvents.observe(viewLifecycleOwner, Observer {

            currentEvents.addAll(ArrayList(it))
            currentEvents.sortBy { it.start }
            currentEvents = currentEvents.filter { it.start.toString() != "null" } as ArrayList<Event>


            for (event in currentEvents) {
                var currentHour = event.start!! / 60
                var currentHourEvents: ArrayList<Event> =
                    currentEvents.filter { e: Event -> (e.start!! / 60 == currentHour) } as ArrayList<Event>
                currentEvents.removeAll(currentHourEvents)
                var i=0
                when (event.day){
                    cal.get(Calendar.DATE) ->{
                        i=0
                    }
                    cal2.get(Calendar.DATE) ->{
                        i=1
                    }
                    else -> {
                        i=2
                    }
                }

                items[currentHour][i] = weekgridEvent(currentHour, currentHourEvents)

                adapter.notifyDataSetChanged()
            }

        })


    }


    fun initWeek(view: View) {

        val sdf = SimpleDateFormat("MM-dd")
        firstDate = cal.get(Calendar.DAY_OF_YEAR)

        initWeekCalendar()
        for (i in 1..3) {
            val output = sdf.format(cal.time)
            val tv = view.findViewWithTag<TextView>("week_day_" + i)
            tv.text = output


            cal.add(Calendar.DAY_OF_YEAR, 1)
        }




    }

    fun initWeekCalendar() {
        items.clear()
        var emptyEvents = arrayListOf<Event>()
        for (i in 0..23) {
            val item = ArrayList<weekgridEvent>()
            for (j in 0..2) {

                item.add(weekgridEvent(i, emptyEvents))
            }
            items.add(item)
        }
        adapter.notifyDataSetChanged()
        populateWeekCalendar()
    }

    fun prevWeek(view: View) {
        val sdf = SimpleDateFormat("MM-dd")


        firstDate -= 1
        if (firstDate == 0){
            cal.add(Calendar.YEAR, -1)
            firstDate = 365
        }
        println("First Date After: " + firstDate)
        cal.set(Calendar.DAY_OF_YEAR, firstDate)
        for (i in 1..3) {

            val output = sdf.format(cal.time)
            val tv = view.findViewWithTag<TextView>("week_day_" + i)
            tv.text = output


            cal.add(Calendar.DAY_OF_YEAR, 1)


        }

        initWeekCalendar()

    }

    fun nextWeek(view: View) {
        val sdf = SimpleDateFormat("MM-dd")
        firstDate += 1
        if (firstDate == 366){
            cal.add(Calendar.YEAR, 1)
            firstDate = 1
        }
        cal.set(Calendar.DAY_OF_YEAR, firstDate)

        for (i in 1..3) {

            val output = sdf.format(cal.time)
            val tv = view.findViewWithTag<TextView>("week_day_" + i)
            tv.text = output
            cal.add(Calendar.DAY_OF_YEAR, 1)
        }

        initWeekCalendar()
    }
}
