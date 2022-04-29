package com.example.final_project.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.final_project.R
import com.example.final_project.adapters.EventAdapter.Companion.EVENT_EXTRA_IMAGE
import com.example.final_project.adapters.EventAdapter.Companion.EVENT_EXTRA_NAME
import com.example.final_project.models.ImageX

open class DetailEvent : AppCompatActivity() {
    lateinit var tvTitle: TextView
    lateinit var ivImage: ImageView
    lateinit var btnRespond: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        tvTitle = findViewById(R.id.tvTitle)
        ivImage = findViewById(R.id.ivImage)
        btnRespond = findViewById(R.id.btnRespond)
        val eventName = intent.getStringExtra(EVENT_EXTRA_NAME)
        val eventImg = intent.getParcelableExtra<ImageX>(EVENT_EXTRA_IMAGE) as ImageX
        tvTitle.text = eventName
        btnRespond.text = "Buy tickets"
        Glide.with(this).load(eventImg.url).into(ivImage)

        btnRespond.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")))
        }
    }
}