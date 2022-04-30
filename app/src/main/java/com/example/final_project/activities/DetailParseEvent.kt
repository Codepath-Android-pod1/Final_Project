package com.example.final_project.activities

import android.os.Bundle
import android.util.Log
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
import com.parse.ParseQuery
import com.parse.ParseUser
import org.json.JSONArray

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
        val parentView: View = findViewById(R.id.activity_detail_event)

        tvTitle = findViewById(R.id.tvTitle)
        tvDetails = findViewById(R.id.tvDetails)
        btnRespond = findViewById(R.id.btnRespond)
        tvDetailLoc1 = findViewById(R.id.tvDetailLoc1)
        tvDetailLoc2 = findViewById(R.id.tvDetailLoc2)

        val event = intent.getParcelableExtra<ParseEvent>(EVENT_EXTRA) as ParseEvent
        val query = ParseQuery.getQuery(ParseEvent::class.java)
        val updatedEvent = query.get(event.objectId)
        // Convert JSONArray to MutableList to allow searching
        registered = JsonToList(updatedEvent.getLogistics())
        val currUsername = ParseUser.getCurrentUser().username
        val eventCreator = event.getUser()!!.fetchIfNeeded().username
        val userRegistered = registered!!.contains(currUsername)
        Log.i(TAG, "User registered: $userRegistered")

        tvTitle.text = event.getTitle()
        tvDetails.text = event.getDescription()
        tvDetailLoc1.text = event.getLocName()
        tvDetailLoc2.text = event.getLocAddress()
        ivImage = findViewById(R.id.ivImage)
        ivImage.isVisible = false
        val btnText = when {
            currUsername == eventCreator -> "Edit"
            userRegistered -> "Registered"
            else -> "Register"
        }
        btnRespond.text = btnText

        btnRespond.setOnClickListener {
            if (currUsername == eventCreator) {
                // TODO Current user is event creator
            } else {
                val currUserRegistered = registered!!.contains(currUsername)
                // Check if `registered` field is empty. If so, create new object
                if (registered!!.isEmpty()) {
                    val logs = JSONArray()
                    registered!!.add(currUsername)
                    logs.put(0, registered)
                    event.setLogistics(logs)
                    event.saveInBackground()
                    Snackbar.make(parentView, "Successfully registered!", Snackbar.LENGTH_SHORT)
                        .show()
                    btnRespond.text = "Registered"
                } else {
                    // Check if current user is registered and update UI accordingly
                    if (currUserRegistered) {
                        // Remove user from registration list
                        val newLogs = event.getLogistics()!!
                        registered!!.remove(currUsername)
                        newLogs.put(0, registered)
                        event.setLogistics(newLogs)
                        event.saveInBackground()
                        btnRespond.text = "Register"
                    } else {
                        // Register user
                        val newLogs = event.getLogistics()!!
                        registered!!.add(currUsername)
                        newLogs.put(0, registered)
                        event.setLogistics(newLogs)
                        event.saveInBackground()
                        Snackbar.make(parentView, "Successfully registered!", Snackbar.LENGTH_SHORT)
                            .show()
                        btnRespond.text = "Registered"
                    }
                }
            }
        }
    }

    companion object {
        const val TAG = "DetailParseEvent"

        fun JsonToList(jsonArray: JSONArray?): MutableList<String> {
            val list = mutableListOf<String>()
            val data = jsonArray?.get(0) as JSONArray?
            if (data != null) {
                for (i in 0 until data.length()) {
                    list.add(data.get(i) as String)
                }
            }
            return list
        }
    }
}
