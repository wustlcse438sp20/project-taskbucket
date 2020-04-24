package com.example.taskbucket.fragments


import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.databasefinal.Event
import com.example.taskbucket.R
import com.example.taskbucket.adapters.TaskItem
import com.example.taskbucket.adapters.bucketAdapter
import com.example.taskbucket.adapters.projectAdapter
import com.example.taskbucket.adapters.taskAdapter
import com.example.taskbucket.util.BucketInstance
import com.example.taskbucket.util.*
import com.example.taskbucket.viewmodels.EventViewModel
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_add_task.view.*
import kotlinx.android.synthetic.main.fragment_bucket.*
import kotlinx.android.synthetic.main.sidebar.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

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
    lateinit var bucketFilters: ArrayList<ImageButton>
    lateinit var sidebarImage: ImageView
    lateinit var viewModel: EventViewModel
    lateinit var addProject: ImageButton
    lateinit var filter: String
    lateinit var sidebarRV: RecyclerView

    var items = arrayListOf<TaskItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //(activity as AppCompatActivity).supportActionBar?
        setHasOptionsMenu(true)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        sidebarRV = activity!!.findViewById<RecyclerView>(R.id.sidebar_rv)
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
        //bucketGroup = view.findViewById(R.id.bucket_group)
        floatingActionButton = view.findViewById(R.id.floatingActionButton)
        recyclerView = view.findViewById(R.id.recyclerView)
        bucketFilters = arrayListOf(bucketFilter1, bucketFilter2, bucketFilter3, bucketFilter4)
        filter  = "all"
        val eventViewModel: EventViewModel by activityViewModels()
        viewModel =  eventViewModel
        drawerLayout = activity!!.findViewById(R.id.drawer_layout)
        //val toggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        //val toggle = (activity as AppCompatActivity).setup
        //drawerLayout.addDrawerListener(toggle)
        //toggle.syncState()
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val adapter = taskAdapter(taskAdapter.eventClickListener(
            { event: Event, view: View ->
                //onclick handler
                Log.d(TAG, "onViewCreated: event tapped " + event.toString())
            },
            { event: Event, view: View ->
                //longpress handler
                Log.d(TAG, "onViewCreated: event long tapped " + event.toString())
                draggedEvent = event
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
            }))
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //set up bucket adaper
        val bucketAdapter = bucketAdapter(bucketAdapter.eventClickListener(
            { bucket, view: View ->
                //onclick handler
                Log.d(TAG, "onViewCreated: event tapped " + bucket.toString())
            },
            { bucket, view: View ->
                //longpress handler
                Log.d(TAG, "onViewCreated: event long tapped " + bucket.toString())

            },
            { view, bucket ->
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
                                (view as MaterialCardView).setCardBackgroundColor(resources.getColor(R.color.bucketHighlight, null))
                            }
                            true
                        }

                        DragEvent.ACTION_DRAG_EXITED ->{
                            Log.d(TAG, "onViewCreated: drag exited " + view.tag)
                            if(view.tag == "bucket"){
                                //view.background.setTint(resources.getColor(R.color.empty, null))
                                view.background.clearColorFilter()
                                (view as MaterialCardView).setCardBackgroundColor(resources.getColor(R.color.bucketNormal, null))
                            }
                            true
                        }

                        DragEvent.ACTION_DROP ->{
                            Log.d(TAG, "onViewCreated: drag dropped " + draggedEvent.name)
                            Log.d(TAG, "onViewCreated: drag dropped " + bucket.toString())
                            if(view.tag == "bucket"){
                                view.background.clearColorFilter()
                                (view as MaterialCardView).setCardBackgroundColor(resources.getColor(R.color.bucketNormal, null))
                                (view as MaterialCardView).setCardBackgroundColor(resources.getColor(R.color.colorPrimaryDark, null))
                                val newEvent = Event(draggedEvent.name, bucket.month, bucket.day, bucket.year!!, draggedEvent.start, draggedEvent.end,draggedEvent.description, project_id = draggedEvent.project_id)
                                Log.d(TAG, "onViewCreated: drag dropped" + newEvent.toString())
                                eventViewModel.updateEvent(id = draggedEvent.id, name = draggedEvent.name, month = bucket.month, week_number = bucket.week, week_day = draggedEvent.week_day, day =  bucket.day, year = bucket.year!!, start = draggedEvent.start, end = draggedEvent.end, description = draggedEvent.description, project_id = draggedEvent.project_id)
                                //runFilter()
                                //view.background.setTint(resources.getColor(R.color.colorPrimaryDark, null))
                            }
                            true
                        }
                    }
                    true
                }
                view.tag = "bucket"
                view.setOnDragListener(dragListen)
            }))
        bucket_rv_buckets.adapter = bucketAdapter
        //bucket_rv_buckets.layoutManager = LinearLayoutManager(requireContext())
        //val test_buckets = arrayListOf(BucketInstance(1, "week"),BucketInstance(2, "week"),BucketInstance(3, "week"),BucketInstance(4, "week"),BucketInstance(5, "week"))
        val allBuckets = bucketUtil().getAllBuckets()
        bucket_rv_buckets.layoutManager = GridLayoutManager(requireContext(), allBuckets.size, GridLayoutManager.HORIZONTAL,false)
        bucketAdapter.submitList(allBuckets)
        //set up listeners
        floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_bucketFragment2_to_addEventDialogFragment)
        }

        var currentEventListener = eventViewModel.allEventsLive.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })



        bucket_tablayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {
                true
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                when(p0!!.position){
                    0 -> {
                        Log.d(TAG, "onTabSelected: getting all events")
                        currentEventListener = eventViewModel.allEventsLive.removeObservers(viewLifecycleOwner)
                    }
                    1 -> {
                        currentEventListener = eventViewModel.dayEventsLive.removeObservers(viewLifecycleOwner)

                    }
                    2 -> {
                        currentEventListener = eventViewModel.weekEventsLive.removeObservers(viewLifecycleOwner)

                    }
                    3 -> {
                        currentEventListener = eventViewModel.monthEventsLive.removeObservers(viewLifecycleOwner)

                    }
                    4 -> {
                        currentEventListener = eventViewModel.yearEventsLive.removeObservers(viewLifecycleOwner)
                    }
                }
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                val calendar = Calendar.getInstance()
                Log.d(TAG, "onTabSelected: year, " + calendar.get(Calendar.YEAR))
                Log.d(TAG, "onTabSelected: month, " + calendar.get(Calendar.MONTH))
                Log.d(TAG, "onTabSelected: day, " + calendar.get(Calendar.DAY_OF_MONTH))
                Log.d(TAG, "onTabSelected: week, " + calendar.get(Calendar.WEEK_OF_MONTH))
                when(p0!!.position){
                    0 -> {
                        Log.d(TAG, "onTabSelected: getting all events")
                        filter = "All"
                        currentEventListener = eventViewModel.allEventsLive.observe(viewLifecycleOwner, Observer {
                            Log.d(TAG, "onTabSelected: observed new data: " + it.size)
                            adapter.submitList(it)
                            Log.d(TAG, "onTabSelected: adding  data to list: " + adapter.itemCount)
                        })
                    }
                    1 -> {
                        filter = "Day"
                        currentEventListener = eventViewModel.dayEventsLive.observe(viewLifecycleOwner, Observer {
                            adapter.submitList(it)
                        })
                        //eventViewModel.getEventsByDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                    }
                    2 -> {
                        filter = "Week"
                        currentEventListener = eventViewModel.weekEventsLive.observe(viewLifecycleOwner, Observer {
                            adapter.submitList(it)
                        })
                        //eventViewModel.getEventsByMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH))
                    }
                    3 -> {
                        filter = "Month"
                        currentEventListener = eventViewModel.monthEventsLive.observe(viewLifecycleOwner, Observer {
                            adapter.submitList(it)
                        })
                        //eventViewModel.getEventsByWeek(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.WEEK_OF_MONTH))
                    }
                    4 -> {
                        currentEventListener = eventViewModel.yearEventsLive.observe(viewLifecycleOwner, Observer {
                            adapter.submitList(it)
                        })
                        filter = "Year"
                        //eventViewModel.getEventsByYear(calendar.get(Calendar.YEAR))
                    }
                }
            }

        })
        //sidebarRV = view.findViewById(R.id.recyclerView)
        sidebarRV.layoutManager = LinearLayoutManager(requireContext())
        val pAdapter = projectAdapter(projectAdapter.eventClickListener{
                item, view ->
            Log.d(TAG, "onViewCreated: "  + item.name)
            eventViewModel.updateProject(item)
            findNavController().navigate(R.id.action_global_projectFragment)

        })
        sidebarRV.adapter = pAdapter
        //eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        eventViewModel!!.projectsLive.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreate: " + "projects changed")
            //eventViewModel.deleteOneProject(it[0].id)
            pAdapter.submitList(it)
            Log.d(TAG, "onCreate: " + pAdapter.itemCount)
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: clicked, " + item.itemId)
        when(item.itemId){
            R.id.action_show_buckets -> {
                if(bucket_rv_buckets.visibility == View.VISIBLE){
                    bucket_rv_buckets.visibility = View.GONE
                }else{
                    bucket_rv_buckets.visibility = View.VISIBLE
                }
            }
            R.id.action_filter -> {
                if(bucket_tablayout.visibility == View.VISIBLE){
                    bucket_tablayout.visibility = View.GONE
                }else{
                    bucket_tablayout.visibility = View.VISIBLE
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun runFilter(){
        val calendar = Calendar.getInstance()
        when(filter){
            "All" ->{
                viewModel.getAll()
            }
            "Day" ->{
                viewModel.getEventsByDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            }
            "Week" ->{
                viewModel.getEventsByWeek(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.WEEK_OF_MONTH))
            }
            "Month" ->{
                viewModel.getEventsByMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH))
            }
            "Year" ->{
                viewModel.getEventsByYear(calendar.get(Calendar.YEAR))
            }
        }

    }
}
