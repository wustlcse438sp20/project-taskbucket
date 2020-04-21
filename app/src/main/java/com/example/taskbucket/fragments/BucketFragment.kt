package com.example.taskbucket.fragments


import android.app.AlertDialog
import android.content.ClipData
import android.content.DialogInterface
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.constraintlayout.widget.Group
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.databasefinal.Event
import com.example.taskbucket.R
import com.example.taskbucket.adapters.TaskItem
import com.example.taskbucket.adapters.taskAdapter
import com.example.taskbucket.viewmodels.EventViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.dialog_add_task.view.*
import kotlinx.android.synthetic.main.fragment_bucket.*

/**
 * A simple [Fragment] subclass.
 */
class BucketFragment : Fragment() {
    val TAG: String = "BucketFragment"
    lateinit var filterGroup: Group
    lateinit var bucketGroup: Group
    lateinit var floatingActionButton: FloatingActionButton
    lateinit var recyclerView: RecyclerView
    lateinit var drawerLayout: DrawerLayout
    lateinit var draggedEvent: Event
    var items = arrayListOf<TaskItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //(activity as AppCompatActivity).supportActionBar?
        setHasOptionsMenu(true)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_bucket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filterGroup = view.findViewById(R.id.filter_group)
        bucketGroup = view.findViewById(R.id.bucket_group)
        floatingActionButton = view.findViewById(R.id.floatingActionButton)
        recyclerView = view.findViewById(R.id.recyclerView)
        val eventViewModel: EventViewModel by activityViewModels()
        drawerLayout = activity!!.findViewById(R.id.drawer_layout)
        //val toggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        //val toggle = (activity as AppCompatActivity).setup
        //drawerLayout.addDrawerListener(toggle)
        //toggle.syncState()
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val adapter = taskAdapter(taskAdapter.eventClickListener(
            { event: Event, view: View ->
                //onclick handler
                Log.d(TAG, "onViewCreated: event tapped " + event.name)
            },
            { event: Event, view: View ->
                //longpress handler
                Log.d(TAG, "onViewCreated: event long tapped " + event.name)
                val dl = View.OnDragListener { view, dragEvent ->
                    when(dragEvent.action){
                        DragEvent.ACTION_DRAG_STARTED -> {
                            view.setBackgroundColor(resources.getColor(R.color.bucketHighlight, null))
                            true
                        }

                        DragEvent.ACTION_DRAG_ENDED ->{
                            Log.d(TAG, "onViewCreated: drag exited " + view.tag)
                            view.background.setTint(resources.getColor(R.color.transparent, null))
                            true
                        }
                    }
                    true
                }
                view.setOnDragListener(dl)
                draggedEvent = event
                view.startDragAndDrop(
                    null,
                    View.DragShadowBuilder(view),
                    null,
                    0
                )
            },
            {
                eventViewModel.deleteOne(it.id)
                eventViewModel.getAll()
            }))
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //set up listeners
        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_bucketFragment2_to_addEventDialogFragment)
        }
        eventViewModel.getAll()
        eventViewModel.currentEvents.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onViewCreated: size of all events: " + it.size)
            adapter.submitList(it)
        })

        val dragListen = View.OnDragListener{view, event ->
            when(event.action){
                DragEvent.ACTION_DRAG_STARTED ->{
                    true
                }
                DragEvent.ACTION_DRAG_ENTERED ->{
                    Log.d(TAG, "onViewCreated: drag entered " + view.tag)
                    if(view.tag == "bucket"){
                        //view.background.setTint(resources.getColor(R.color.bucketHighlight, null))
                        view.background.colorFilter = PorterDuffColorFilter(resources.getColor(R.color.bucketHighlight, null),PorterDuff.Mode.SRC_ATOP)
                    }
                    true
                }

                DragEvent.ACTION_DRAG_EXITED ->{
                    Log.d(TAG, "onViewCreated: drag exited " + view.tag)
                    if(view.tag == "bucket"){
                        //view.background.setTint(resources.getColor(R.color.empty, null))
                        view.background.clearColorFilter()
                    }
                    true
                }

                DragEvent.ACTION_DROP ->{
                    Log.d(TAG, "onViewCreated: drag dropped " + draggedEvent.name)
                    if(view.tag == "bucket"){
                        view.background.clearColorFilter()
                        //view.background.setTint(resources.getColor(R.color.colorPrimaryDark, null))
                    }
                    true
                }
            }
            true
        }

        bucket1.setOnDragListener(dragListen)
        bucket2.setOnDragListener(dragListen)
        bucket3.setOnDragListener(dragListen)
        bucket4.setOnDragListener(dragListen)
        bucket5.setOnDragListener(dragListen)

    }

    fun addTaskDialog(){
        val dialogView = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_add_task, null)
        val mBuilder = AlertDialog.Builder(requireActivity())
            .setView(dialogView)
            .setTitle("Add To Playlist")
            .setPositiveButton("Confirm", DialogInterface.OnClickListener { dialog, switch ->
                val task = dialogView.editText.text.toString()
                if(task.isNotBlank()){
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

            android.R.id.home ->{
                //drawerLayout.openDrawer(drawerLayout)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
