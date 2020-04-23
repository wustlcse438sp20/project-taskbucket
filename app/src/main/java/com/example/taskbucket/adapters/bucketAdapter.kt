package com.example.taskbucket.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskbucket.R
import com.example.taskbucket.util.BucketInstance

class bucketAdapter(val clickListener: eventClickListener): ListAdapter<BucketInstance, bucketAdapter.ViewHolder>(bucketAdapter.diffUtilItemCallback()){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.listitem_bucket_name)
        //val checkBox: CheckBox = view.findViewById(R.id.checkBox)
        val view = view
        fun bind(bucketInstance: BucketInstance, eventClickListener: eventClickListener){
            eventClickListener.setUpView(view, bucketInstance)
            name.text = bucketInstance.name
            this.view.setOnClickListener {
                eventClickListener.onClick(bucketInstance, it)
            }
            this.view.setOnLongClickListener {
                eventClickListener.onLongPress(bucketInstance, it)
                true
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.taskDescription.text = getItem(position).name
        holder.bind(getItem(position), clickListener)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_bucket, parent, false)
        return ViewHolder(view)
    }

    class diffUtilItemCallback: DiffUtil.ItemCallback<BucketInstance>(){
        override fun areItemsTheSame(oldItem: BucketInstance, newItem: BucketInstance): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: BucketInstance, newItem: BucketInstance): Boolean {
            return oldItem.name == newItem.name
        }

    }

    class eventClickListener(val onSingleClick:(bucketInstance: BucketInstance, view: View) -> Unit,
                             val onLongCLick:(bucketInstance: BucketInstance, view: View) -> Unit,
                             val setView:(view: View, bucketInstance: BucketInstance) -> Unit){
        fun onClick(bucketInstance: BucketInstance, view: View) = onSingleClick(bucketInstance,view)
        fun onLongPress(bucketInstance: BucketInstance, view: View) = onLongCLick(bucketInstance,view)
        fun setUpView(view: View, bucketInstance: BucketInstance) = setView(view, bucketInstance)
    }
}