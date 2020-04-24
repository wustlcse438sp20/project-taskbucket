package com.example.taskbucket.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.databasefinal.Event
import com.example.taskbucket.R
import com.example.taskbucket.viewmodels.EventViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_fragment_add_event.*

class addEventDialogFragment : DialogFragment(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog)
        //activity?.window?.requestFeature(Window.FEATURE_ACTION_MODE_OVERLAY)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.dialog_fragment_add_event,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_adding_text.setDialog(this)
        val eventViewModel: EventViewModel by activityViewModels()
        bttn_adding_send.setOnClickListener {
            if(et_adding_text.text.isNotBlank()){
                if(eventViewModel.addProjectCheck.value == true){
                    eventViewModel.addProjectCheck.value = false
                    eventViewModel.insert(Event(name = et_adding_text.text.toString(), year = 2020, project_id = eventViewModel.projectLive.value!!.id))
                    eventViewModel.getAll()
                    dismiss()
                }else{
                    eventViewModel.insert(Event(name = et_adding_text.text.toString(), year = 2020))
                    eventViewModel.getAll()
                    dismiss()
                }

            }
        }

    }
}