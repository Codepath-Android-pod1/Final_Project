package com.example.final_project.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.final_project.R
import com.example.final_project.adapters.ParseEventAdapter.Companion.EVENT_EXTRA
import com.example.final_project.models.ParseEvent
import com.google.android.material.snackbar.Snackbar
import com.parse.ParseObject
import com.parse.ParseUser

class DetailParseEvent : AppCompatActivity() {
    private lateinit var tvTitle: TextView
    private lateinit var tvDetails: TextView
    private lateinit var ivImage: ImageView
    private lateinit var btnRespond: Button
    private lateinit var tvDetailLoc1: TextView
    private lateinit var tvDetailLoc2: TextView
    private var registered: MutableList<String>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)
        val parentView = findViewById<View>(R.id.activity_detail_event)

        tvTitle = findViewById(R.id.tvTitle)
        tvDetails = findViewById(R.id.tvDetails)
        btnRespond = findViewById(R.id.btnRespond)
        tvDetailLoc1 = findViewById(R.id.tvDetailLoc1)
        tvDetailLoc2 = findViewById(R.id.tvDetailLoc2)

        val event = intent.getParcelableExtra<ParseEvent>(EVENT_EXTRA) as ParseEvent
        registered = event.getLogistics()?.getList("registered")
        val currUsername = ParseUser.getCurrentUser().username
        val eventCreator = event.getUser()!!.fetchIfNeeded().username
        val userRegistered = registered?.contains(currUsername)

        tvTitle.text = event.getTitle()
        tvDetails.text = event.getDescription()
        tvDetailLoc1.text = event.getLocName()
        tvDetailLoc2.text = event.getLocAddress()
        ivImage = findViewById(R.id.ivImage)
        ivImage.isVisible = false
        val btnText = when {
            currUsername == eventCreator -> "Edit"
            userRegistered == true -> "Registered"
            else -> "Register"
        }
        btnRespond.text = btnText

        btnRespond.setOnClickListener {
            registered = event.getLogistics()?.getList("registered")
            val currUserRegistered = registered?.contains(currUsername)
            if (currUsername == eventCreator) {
                // TODO Current user is event creator
            } else {
                // Check if `registered` field is empty. If so, create new object
                if (registered == null) {
                    btnRespond.text = "Registered"
                    val logs = ParseObject("logistics")
                    logs.put("registered", mutableListOf(currUsername))
                    event.setLogistics(logs)
                    Snackbar.make(parentView, "Successfully registered!", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    // Check if current user is registered and update UI accordingly
                    if (currUserRegistered == true) {
                        // Remove user from registration list
                        btnRespond.text = "Register"
                        val newLogs = event.getLogistics()!!
                        newLogs.put("registered", registered!!.remove(currUsername))
                        event.setLogistics(newLogs)
                    } else {
                        // Register user
                        btnRespond.text = "Registered"
                        val newLogs = event.getLogistics()!!
                        newLogs.put("registered", registered!!.add(currUsername))
                        event.setLogistics(newLogs)
                        Snackbar.make(parentView, "Successfully registered!", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}
