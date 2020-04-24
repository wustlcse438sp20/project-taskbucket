package com.example.taskbucket.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.databasefinal.Event
import com.example.databasefinal.Project
import com.example.taskbucket.R


class projectAdapter(val clickListener: eventClickListener): ListAdapter<Project, projectAdapter.ViewHolder>(projectAdapter.diffUtilItemCallback()){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.listitem_projectname)
        val view = view
        fun bind(Event: Project, eventClickListener: eventClickListener){
            val a = R.color.colorAccent
            name.setTextColor(Color.GRAY)
            name.visibility = View.VISIBLE
            name.text = Event.name
            this.view.setOnClickListener {
                eventClickListener.onClick(Event, it)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.taskDescription.text = getItem(position).name
        holder.bind(getItem(position), clickListener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_project, parent, false)
        return ViewHolder(view)
    }

    class diffUtilItemCallback: DiffUtil.ItemCallback<Project>(){
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem == newItem
        }

    }

    class eventClickListener(val onSingleClick:(Event: Project, view: View) -> Unit){
        fun onClick(Event: Project, view: View) = onSingleClick(Event,view)
    }
}