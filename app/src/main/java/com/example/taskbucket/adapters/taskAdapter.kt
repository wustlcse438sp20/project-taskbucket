package com.example.taskbucket.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.taskbucket.R

data class TaskItem(val desc: String)

class taskAdapter(items: ArrayList<TaskItem>): RecyclerView.Adapter<taskAdapter.ViewHolder>(){
    var items: ArrayList<TaskItem> = items

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val taskDescription: TextView = view.findViewById(R.id.task_description)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.taskDescription.text = items[position].desc
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_task, parent, false)
        return ViewHolder(view)
    }
}