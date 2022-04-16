package com.example.final_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.final_project.fragments.ChatFragment
import com.example.final_project.fragments.EditFragment
import com.example.final_project.fragments.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.parse.ParseObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstObject = ParseObject("FirstClass")
        firstObject.put("message","Hey ! First message from android. Parse is now connected")
        firstObject.saveInBackground {
            if (it != null){
                it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
            }else{
                Log.d("MainActivity","Object saved.")
            }
        }

        // Fragments / Bottom Navigation Bar
        val fragmentManager: FragmentManager = supportFragmentManager
        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener { item ->
            var fragmentToshow: Fragment? = null
            when(item.itemId) {
                R.id.action_Home -> {
                    fragmentToshow = HomeFragment()
                }
                R.id.action_Edit -> {
                    fragmentToshow = EditFragment()
                }
                R.id.action_Chat -> {
                    fragmentToshow = ChatFragment()
                }
            }
            if(fragmentToshow != null) {
                fragmentManager.beginTransaction().replace(R.id.FragmentContainer, fragmentToshow).commit()
            }
            true
        }
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_Home

        // Generalize Tool bar (We could move it to individual Fragment and have specialize toolbar
        // Actually we might have to us e Toolbar for each Fragments
        setSupportActionBar(findViewById(R.id.Main_Toolbar))
    }

    companion object {
        const val TAG = "MainActivity"
    }
}