package com.example.taskbucket.adapters

import android.app.Activity
import android.app.AlertDialog
import android.nfc.Tag
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
        val linearLayout: LinearLayout = view.findViewById(R.id.day_event_linear)
        val view = view
        fun expandView(start:Int, end:Int){
            textView.layoutParams.height = textView.layoutParams.height * (end - start)
        }
        fun editEvent(view:View){
            val dialogView =
                LayoutInflater.from(view.context).inflate(R.layout.edit_event_info, null)
            val mBuilder = AlertDialog.Builder(view.context)
                .setView(dialogView)
                .setTitle("Edit Info")
            val mAlertDialog = mBuilder.show()
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].name
        //holder.textView.x = holder.textView.x * 2
        holder.expandView(items[position].start!!, items[position].end!!)
        holder.textView.setOnClickListener{
            holder.editEvent(it)
        }
        holder.linearLayout.elevation = 1F
//        Log.d(TAG, "onBindViewHolder: " + items[position].name)
//        Log.d(TAG, "TextView Height: " + holder.textView.height)
//        Log.d(TAG, "LinearLayout elevation: " + holder.linearLayout.elevation)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_day_event, parent, false)
        return ViewHolder(view)
    }


}