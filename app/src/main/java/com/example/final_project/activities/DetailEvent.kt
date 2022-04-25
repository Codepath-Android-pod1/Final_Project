package com.example.final_project.activities

import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        tvTitle = findViewById(R.id.tvTitle)
        ivImage = findViewById(R.id.ivImage)
        val eventName = intent.getStringExtra(EVENT_EXTRA_NAME)
        val eventImg = intent.getParcelableExtra<ImageX>(EVENT_EXTRA_IMAGE) as ImageX
        tvTitle.text = eventName
        Glide.with(this).load(eventImg.url).into(ivImage)
    }
}