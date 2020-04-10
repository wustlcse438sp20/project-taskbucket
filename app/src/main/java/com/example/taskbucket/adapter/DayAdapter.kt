package com.example.taskbucket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskbucket.R
import com.example.taskbucket.data.Events

class DayViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.event_item, parent, false)){
    private val eventText: TextView
    private val linearLayout: LinearLayout

    init {

        eventText = itemView.findViewById(R.id.event_text)
        linearLayout = itemView.findViewById(R.id.event_layout)
    }

    fun bind(events: ArrayList<Events>, position: Int){
        if(events.isNotEmpty()){
            for(event in events){
                eventText.text = event.title
            }
        }



    }


}

class DayAdapter (private val list: ArrayList<ArrayList<Events>>) : RecyclerView.Adapter<DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DayViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val events: ArrayList<Events> = list[position]
        holder.bind(events, position)
    }

    override fun getItemCount(): Int = list.size
}