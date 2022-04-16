package com.example.final_project

import android.content.Intent
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
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseUser


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
            val firstObject = ParseObject("FirstClass")
            firstObject.put("message","Hey ! First message from android. Parse is now connected")
            firstObject.saveInBackground {
                if (it != null){
                    it.localizedMessage?.let { message -> Log.e("MainActivity", message) }
                }else{
                    Log.d("MainActivity","Object saved.")
                }
            }
        */

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
                fragmentManager
                    .beginTransaction()
                    .replace(R.id.FragmentContainer, fragmentToshow)
                    .commit()
            }
            true
        }
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_Home

        // Generalize Tool bar (We could move it to individual Fragment and have specialize toolbar
        // Actually we might have to us e Toolbar for each Fragments
        val toolbar = findViewById<Toolbar>(R.id.Main_Toolbar)
        setSupportActionBar(findViewById(R.id.Main_Toolbar))
        toolbar.title = ""
        toolbar.setNavigationIcon(R.drawable.ic_launcher_background)
        toolbar.setNavigationOnClickListener{
            Toast.makeText(this, "Please work", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id){
            R.id.Logout ->{
                ParseUser.logOut()
                goToLogin()
                Toast.makeText(this, "Testing Complete", Toast.LENGTH_SHORT).show()
            }
            R.id.Profile -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun goToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}