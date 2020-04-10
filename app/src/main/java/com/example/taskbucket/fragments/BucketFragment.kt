package com.example.taskbucket.fragments


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskbucket.R
import com.example.taskbucket.adapters.TaskItem
import com.example.taskbucket.adapters.taskAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.dialog_add_task.view.*

/**
 * A simple [Fragment] subclass.
 */
class BucketFragment : Fragment() {
    val TAG: String = "BucketFragment"
    lateinit var filterGroup: Group
    lateinit var bucketGroup: Group
    lateinit var floatingActionButton: FloatingActionButton
    lateinit var recyclerView: RecyclerView
    var items = arrayListOf<TaskItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //(activity as AppCompatActivity).supportActionBar?
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_bucket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        filterGroup = view.findViewById(R.id.filter_group)
        bucketGroup = view.findViewById(R.id.bucket_group)
        floatingActionButton = view.findViewById(R.id.floatingActionButton)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.adapter = taskAdapter(items)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //set up listeners
        floatingActionButton.setOnClickListener {
            addTaskDialog()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    fun addTaskDialog(){
        val dialogView = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_add_task, null)
        val mBuilder = AlertDialog.Builder(requireActivity())
            .setView(dialogView)
            .setTitle("Add To Playlist")
            .setPositiveButton("Confirm", DialogInterface.OnClickListener { dialog, switch ->
                val task = dialogView.editText.text.toString()
                if(task.isNotBlank()){
                    items.add(TaskItem(task))
                    recyclerView.adapter = taskAdapter(items)
                    Toast.makeText(requireContext(), "Task added", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }else{
                    Toast.makeText(requireContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                }
            })
        val mAlertDialog = mBuilder.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: clicked, " + item.itemId)
        when(item.itemId){
            R.id.action_show_buckets -> {
                if(bucketGroup.visibility == View.VISIBLE){
                    bucketGroup.visibility = View.GONE
                }else{
                    bucketGroup.visibility = View.VISIBLE
                }
            }
            R.id.action_show_sidebar -> {

            }
            R.id.action_filter -> {
                if(filterGroup.visibility == View.VISIBLE){
                    filterGroup.visibility = View.GONE
                }else{
                    filterGroup.visibility = View.VISIBLE
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
