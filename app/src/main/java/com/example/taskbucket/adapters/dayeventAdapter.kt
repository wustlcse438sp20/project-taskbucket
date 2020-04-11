package com.example.taskbucket.adapters

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.databasefinal.Event
import com.example.taskbucket.R

class dayeventAdapter(items: ArrayList<Event>, activity: Activity): RecyclerView.Adapter<dayeventAdapter.ViewHolder>(){
    val TAG: String = "dayeventAdapter"
    var items: ArrayList<Event> = items
    var activity = activity
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.textView6)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].name
        //holder.recyclerView.adapter
        Log.d(TAG, "onBindViewHolder: " + items[position].name)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_day_event, parent, false)
        return ViewHolder(view)
    }
}