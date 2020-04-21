package com.example.taskbucket.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databasefinal.Event
import com.example.taskbucket.R

data class TaskItem(val desc: String)

class taskAdapter(val clickListener: eventClickListener): ListAdapter<Event, taskAdapter.ViewHolder>(taskAdapter.diffUtilItemCallback()){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val taskDescription: TextView = view.findViewById(R.id.task_description)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)
        val view = view
        fun bind(Event: Event, eventClickListener: eventClickListener){
            taskDescription.text = Event.name
            this.view.setOnClickListener {
                eventClickListener.onClick(Event, it)
            }
            this.view.setOnLongClickListener {
                eventClickListener.onLongPress(Event, it)
                true
            }

            this.checkBox.setOnClickListener {
                eventClickListener.checkPress(Event)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.taskDescription.text = getItem(position).name
        holder.bind(getItem(position), clickListener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_task, parent, false)
        return ViewHolder(view)
    }

    class diffUtilItemCallback: DiffUtil.ItemCallback<Event>(){
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class eventClickListener(val onSingleClick:(Event: Event, view: View) -> Unit,
                             val onLongCLick:(Event: Event, view: View) -> Unit,
                             val checkPress:(Event:Event) -> Unit){
        fun onClick(Event: Event, view: View) = onSingleClick(Event,view)
        fun onLongPress(Event: Event, view: View) = onLongCLick(Event,view)
        fun checkBoxPress(Event: Event) = checkPress(Event)
    }
}