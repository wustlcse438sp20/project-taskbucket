package com.example.taskbucket.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.taskbucket.R
import com.example.taskbucket.adapter.WeekAdapter
import kotlinx.android.synthetic.main.fragment_month.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private var eventList: ArrayList<String> = ArrayList()
private var dateList: ArrayList<String> = ArrayList()
private var cal: Calendar = Calendar.getInstance()
private lateinit var adapter: WeekAdapter
private var month = 0
private var year = 0
class MonthFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_month, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.month_recycler_view)
        adapter = WeekAdapter(dateList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this.context, 7)

        initMonth(view)

        month_prev_button.setOnClickListener {
            prevMonth(view)
        }

        month_next_button.setOnClickListener {
            nextMonth(view)
        }
    }

    fun initMonth(view: View){
        var sdf = SimpleDateFormat("MMMM-yyyy")

        month_title.text = sdf.format(cal.time)
        month = cal.get(Calendar.MONTH)


        sdf = SimpleDateFormat("dd")

        cal.set(Calendar.DAY_OF_MONTH,1)
        cal.get(Calendar.WEEK_OF_YEAR)
        cal.set(Calendar.DAY_OF_WEEK,1)

        for(i in 0..41){
            dateList.add(sdf.format(cal.time))
            cal.add(Calendar.DATE, 1)
        }


        adapter.notifyDataSetChanged()
    }

    fun prevMonth(view:View){
        month -=1

        cal.set(Calendar.MONTH,month)

        dateList.clear()
        initMonth(view)
    }


    fun nextMonth(view:View){
        month += 1
        cal.set(Calendar.MONTH , month)

        dateList.clear()
        initMonth(view)
    }

}
