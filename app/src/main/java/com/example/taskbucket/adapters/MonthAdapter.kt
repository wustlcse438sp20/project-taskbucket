package com.example.taskbucket.adapters

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.databasefinal.Event
import com.example.taskbucket.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton


data class monthEvent(val date: String, val hasEvent: Boolean, val inMonth: Boolean)
class MonthViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.event_item, parent, false)){
    private val eventText: TextView
    private val eventIcon: FloatingActionButton

    init {
        eventText = itemView.findViewById(R.id.event_text)
        eventIcon = itemView.findViewById(R.id.event_icon)
    }

    fun bind(event: monthEvent, position: Int){
        //if(event.inMonth){
            eventText.text = event.date
        //}
        if(event.hasEvent){
            eventIcon.visibility = FloatingActionButton.VISIBLE
        }

    }


}

class MonthAdapter (private val list: ArrayList<monthEvent>) : RecyclerView.Adapter<MonthViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MonthViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        val event: monthEvent = list[position]
        holder.bind(event, position)
    }

    override fun getItemCount(): Int = list.size
}