package com.example.taskbucket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskbucket.R

class WeekViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.event_item, parent, false)){
    private val eventText: TextView


    init {
        eventText = itemView.findViewById(R.id.event_text)
    }

    fun bind(event: String, position: Int){
        eventText.text = event
    }


}

class WeekAdapter (private val list: ArrayList<String>) : RecyclerView.Adapter<WeekViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return WeekViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        val event: String = list[position]
        holder.bind(event, position)
    }

    override fun getItemCount(): Int = list.size
}