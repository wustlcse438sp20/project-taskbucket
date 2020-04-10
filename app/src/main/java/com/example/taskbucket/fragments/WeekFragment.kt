package com.example.taskbucket.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.taskbucket.R
import com.example.taskbucket.adapter.WeekAdapter
import kotlinx.android.synthetic.main.fragment_week.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */


private var eventList: ArrayList<String> = ArrayList()
private var cal:Calendar = Calendar.getInstance()
private lateinit var adapter: WeekAdapter
class WeekFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_week, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.week_recycler_view)
        adapter = WeekAdapter(eventList)
        recyclerView.adapter = adapter

        val glm = GridLayoutManager(this.context, 7)
        glm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 4 == 0) {
                    1
                }
                else {
                    2
                }
            }
        }
        recyclerView.layoutManager = glm

        initWeek(view)

        week_prev_button.setOnClickListener {
            prevWeek(view)
        }

        week_next_button.setOnClickListener {
            nextWeek(view)
        }

    }

    fun initWeek(view:View){

        val sdf = SimpleDateFormat("MM-dd-yyyy")

        for(i in 1..3){
            val output = sdf.format(cal.time)
            val tv = view.findViewWithTag<TextView>("week_day_" + i)
            tv.text = output
            cal.add(Calendar.DATE, 1)
        }

        cal.add(Calendar.DATE, -3)
        for(i in 0..95){

                eventList.add(i.toString())

        }

        adapter.notifyDataSetChanged()
    }

    fun prevWeek(view:View){
        val sdf = SimpleDateFormat("MM-dd-yyyy")
        var lastDate = view.findViewWithTag<TextView>("week_day_3").text.toString()

        val date = sdf.parse(lastDate)

        cal.time = date

        for(i in 3 downTo 1){
            cal.add(Calendar.DATE, -1)
            val output = sdf.format(cal.time)
            val tv = view.findViewWithTag<TextView>("week_day_" + i)
            tv.text = output

        }


        adapter.notifyDataSetChanged()
    }

    fun nextWeek(view:View){
        val sdf = SimpleDateFormat("MM-dd-yyyy")

        var lastDate = view.findViewWithTag<TextView>("week_day_1").text.toString()

        val date = sdf.parse(lastDate)

        cal.time = date
        for(i in 1..3){
            cal.add(Calendar.DATE, 1)
            val output = sdf.format(cal.time)
            val tv = view.findViewWithTag<TextView>("week_day_" + i)
            tv.text = output

        }

        adapter.notifyDataSetChanged()
    }
    }
