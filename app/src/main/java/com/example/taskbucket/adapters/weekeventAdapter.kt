package com.example.taskbucket.adapters

import android.app.Activity
import android.app.AlertDialog
import android.nfc.Tag
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.databasefinal.Event
import com.example.taskbucket.R
import com.example.taskbucket.viewmodels.EventViewModel
import kotlinx.android.synthetic.main.edit_event_info.*
import java.util.*
import kotlin.collections.ArrayList

class weekeventAdapter(items: ArrayList<Event>, activity: Activity, fragment: Fragment): RecyclerView.Adapter<weekeventAdapter.ViewHolder>(){
    val TAG: String = "weekeventAdapter"
    var items: ArrayList<Event> = items
    var activity = activity
    var fragment = fragment
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textView: TextView = view.findViewById(R.id.textView6)
        //val linearLayout: LinearLayout = view.findViewById(R.id.week_event_linear)
        val view = view
        fun expandView(start:Int, end:Int){
            textView.layoutParams.height = (textView.layoutParams.height * (end - start)/60)

        }
        fun editEvent(view:View, event: Event, fragment: Fragment){
            Log.d("weekEventAdapter", "Event: " + event)

            val dialogView =
                LayoutInflater.from(view.context).inflate(R.layout.edit_event_info, null)
            val mBuilder = AlertDialog.Builder(view.context)
                .setView(dialogView)
                .setTitle("Edit Event Info")
            val mAlertDialog = mBuilder.show()

            mAlertDialog.edit_title.hint = event.name
            if(event.description != null){
                mAlertDialog.edit_desc.hint = event.description
            }
            val datePicker = mAlertDialog.edit_date
            var eventMonth = event.month!!
            var eventYear = event.year
            var eventweek = event.day!!
            datePicker.init (eventYear, eventMonth, eventweek){
                    view, year, month, week ->

                eventMonth = month
                eventYear = year
                eventweek = week

            }

            val startPicker = mAlertDialog.edit_start_time
            val endPicker = mAlertDialog.edit_end_time

            startPicker.setIs24HourView(true)
            endPicker.setIs24HourView(true)

            var start = event.start!!
            var end = event.end!!

            val startHour = start / 60
            val endHour = end /60

            startPicker.hour = startHour
            endPicker.hour  = endHour

            startPicker.minute = start - 60*startHour
            endPicker.minute = end - 60*endHour

            startPicker.setOnTimeChangedListener{view, hour, minute -> var hour = hour
                start = hour*60 + minute
            }

            endPicker.setOnTimeChangedListener{view, hour, minute -> var hour = hour
                end = hour*60 + minute
            }


            mAlertDialog.submitEventInfo.setOnClickListener {
                var title = mAlertDialog.edit_title.text.toString()
                var desc = mAlertDialog.edit_desc.text.toString()
                if(desc  == "" || title == ""){
                    val myToast = Toast.makeText(view.context,"Please input valid values", Toast.LENGTH_SHORT)
                    myToast.show()
                }
                else{

                    val cal = Calendar.getInstance()
                    cal.set(eventYear,eventMonth,eventweek)
                    val eventWeek = cal.get(Calendar.WEEK_OF_MONTH)
                    val weekday = cal.get(Calendar.DAY_OF_WEEK)

                    val newEvent = Event(title, eventMonth, eventweek, eventYear, start, end, desc, weekday, eventWeek, event.project_id)

                    var viewModel = ViewModelProvider(fragment).get(EventViewModel::class.java)
                    viewModel.deleteOne(event.id)
                    viewModel.insert(newEvent)
                    mAlertDialog.dismiss()



                }



            }
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
            holder.editEvent(it, items[position], fragment)
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_day_event, parent, false)
        return ViewHolder(view)
    }


}