package com.example.taskbucket.adapters

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.databasefinal.Event
import com.example.taskbucket.R
import com.example.taskbucket.fragments.WeekFragment


data class weekgridEvent(val hour: Int, val events: ArrayList<Event>)


class weekgridAdapter(
    items: ArrayList<ArrayList<weekgridEvent>>,
    activity: Activity,
    fragment: Fragment
) : RecyclerView.Adapter<weekgridAdapter.ViewHolder>() {
    val TAG: String = "weekgridAdapter"
    var items: ArrayList<ArrayList<weekgridEvent>> = items
    var activity = activity
    var fragment = fragment


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hour: TextView = view.findViewById(R.id.week_time)
        val recyclerView1: RecyclerView = view.findViewById(R.id.weekgrid_recycler_view1)
        val recyclerView2: RecyclerView = view.findViewById(R.id.weekgrid_recycler_view2)
        val recyclerView3: RecyclerView = view.findViewById(R.id.weekgrid_recycler_view3)
    }

    override fun getItemCount(): Int {
        println("Size: " + items.size)
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.hour.text = items[position][0].hour.toString() + ":00 "

        for (i in 0 until 2) {
            when (i) {
                0 -> {
                    holder.recyclerView1.adapter =
                        weekeventAdapter(items[position][i].events, activity, fragment)

                    //holder.recyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
                    if (items[position][i].events.size == 0) {

                        holder.recyclerView1.layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    } else {
                        var gLayout = GridLayoutManager(activity, items[position][i].events.size)
                        holder.recyclerView1.layoutManager = gLayout

                    }

                }
                1 -> {
                    holder.recyclerView2.adapter =
                        weekeventAdapter(items[position][i].events, activity, fragment)

                    //holder.recyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
                    if (items[position][i].events.size == 0) {

                        holder.recyclerView2.layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    } else {

                        var gLayout = GridLayoutManager(activity, items[position][i].events.size)
                        holder.recyclerView2.layoutManager = gLayout

                    }

                }
                else -> {
                    holder.recyclerView3.adapter =
                        weekeventAdapter(items[position][i].events, activity, fragment)

                    //holder.recyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
                    if (items[position][i].events.size == 0) {

                        holder.recyclerView3.layoutManager =
                            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    } else {

                        var gLayout = GridLayoutManager(activity, items[position][i].events.size)
                        holder.recyclerView3.layoutManager = gLayout

                    }
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_week_grid, parent, false)

        return ViewHolder(view)
    }
}