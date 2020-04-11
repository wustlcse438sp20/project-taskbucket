package com.example.taskbucket.adapters

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.databasefinal.Event
import com.example.taskbucket.R
import com.example.taskbucket.data.Events

data class daygridEvent(val hour: Int, val events: ArrayList<Event>)

class daygridAdapter(items: ArrayList<daygridEvent>, activity: Activity): RecyclerView.Adapter<daygridAdapter.ViewHolder>(){
    val TAG: String = "daygridAdapter"
    var items: ArrayList<daygridEvent> = items
    var activity = activity
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val hour: TextView = view.findViewById(R.id.textView5)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: " + items[position].events.size)
        holder.hour.text = items[position].hour.toString()
        holder.recyclerView.adapter = dayeventAdapter(items[position].events, activity)
        //holder.recyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
        if(items[position].events.size == 0){
            holder.recyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
        }else{
            var gLayout = GridLayoutManager(activity,items[position].events.size)
            holder.recyclerView.layoutManager = gLayout
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_day_grid, parent, false)
        return ViewHolder(view)
    }
}