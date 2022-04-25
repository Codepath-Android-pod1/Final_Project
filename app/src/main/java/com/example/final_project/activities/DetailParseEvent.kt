package com.example.final_project.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.final_project.R
import com.example.final_project.adapters.ParseEventAdapter.Companion.EVENT_EXTRA
import com.example.final_project.models.ParseEvent

class DetailParseEvent : AppCompatActivity() {
    lateinit var tvTitle: TextView
    lateinit var tvDetails: TextView
    lateinit var ivImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        tvTitle = findViewById(R.id.tvTitle)
        tvDetails = findViewById(R.id.tvDetails)
        val event = intent.getParcelableExtra<ParseEvent>(EVENT_EXTRA) as ParseEvent
        tvTitle.text = event.getTitle()
        tvDetails.text = event.getDescription()
    }
}
