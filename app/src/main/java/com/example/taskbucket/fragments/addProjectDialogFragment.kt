package com.example.taskbucket.fragments

import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.databasefinal.Event
import com.example.databasefinal.Project
import com.example.taskbucket.R
import com.example.taskbucket.viewmodels.EventViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_fragment_add_event.*
import kotlinx.android.synthetic.main.dialog_fragment_add_project.*

class addProjectDialogFragment : DialogFragment(){

    lateinit var textInput: TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
        //activity?.window?.requestFeature(Window.FEATURE_ACTION_MODE_OVERLAY)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.dialog_fragment_add_project,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val eventViewModel: EventViewModel by activityViewModels()
        var text = add_project_et_name.editText!!.text.toString()
        //var texta = view.findViewById<TextInputLayout>(R.id.add_project_et_name).editText!!.text.toString()
        var toolbar  = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.add_project_toolbar)
        add_project_toolbar.inflateMenu(R.menu.action_mode_add_project)
        add_project_toolbar.findViewById<ImageButton>(R.id.add_project_close).setOnClickListener {
            dismiss()
        }
        add_project_toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                (R.id.project_add) ->{
                    var text = add_project_et_name.editText!!.text.toString()
                    eventViewModel.insertProject(Project(text))
                    dismiss()
                    true
                }
                else ->{
                    true
                }
            }
        }

    }
}