package com.example.taskbucket.fragments


import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databasefinal.Event
import com.example.databasefinal.Project
import com.example.taskbucket.R
import com.example.taskbucket.adapters.taskAdapter
import com.example.taskbucket.viewmodels.EventViewModel
import kotlinx.android.synthetic.main.fragment_project.*

/**
 * A simple [Fragment] subclass.
 */
class ProjectFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val eventViewModel: EventViewModel by activityViewModels()
        val currentProject: Project = eventViewModel.projectLive.value!!
        project_name.text = currentProject.name
        val adapter = taskAdapter(
            taskAdapter.eventClickListener(
                { event: Event, view: View ->
                    //onclick handler
                },
                { event: Event, view: View ->
                    //longpress handler

                },
                {
                    eventViewModel.deleteOne(it.id)
                }))
        project_rv.adapter = adapter
        project_rv.layoutManager = LinearLayoutManager(requireContext())
        eventViewModel.allEventsLive.observe(viewLifecycleOwner, Observer {
            val l = it.filter { it.project_id == currentProject.id }
            adapter.submitList(l)
        })

        project_floatingActionButton.setOnClickListener {
            eventViewModel.addProjectCheck.value = true
            findNavController().navigate(R.id.action_projectFragment_to_addEventDialogFragment)
        }

    }


}
