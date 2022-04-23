package com.example.final_project.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.final_project.R
import com.example.final_project.activities.MainActivity
import com.parse.ParseUser
import java.io.File

class EditFragment : Fragment() {
    private lateinit var pb: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pb = view.findViewById<View>(R.id.pbLoading) as ProgressBar

        view.findViewById<Button>(R.id.btnCreate).setOnClickListener {
            createEvent(view)
        }
    }

    private fun createEvent(view: View) {
        hideKeyboard(view.findViewById(R.id.composeView))
        val title = view.findViewById<EditText>(R.id.etTitle).text.toString()
        val description = view.findViewById<EditText>(R.id.etDescription).text.toString()
        val user = ParseUser.getCurrentUser()
    }

    private fun hideKeyboard(v: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }
}