package com.example.final_project.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.final_project.R

class HomeFragment : Fragment() {

    lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    companion object {

    }
}