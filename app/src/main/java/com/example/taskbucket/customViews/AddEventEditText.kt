package com.example.taskbucket.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class AddEventEditText(context: Context, attrs: AttributeSet): EditText(context, attrs){
    lateinit var dialogFragment: DialogFragment
    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            dialogFragment.dismiss()
        }
        return super.onKeyPreIme(keyCode, event)
    }

    fun setDialog(dialogFragment: DialogFragment){
        this.dialogFragment = dialogFragment
    }
}