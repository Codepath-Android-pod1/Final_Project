package com.example.final_project.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.models.ParseEvent
import com.parse.ParseGeoPoint
import java.time.format.DateTimeFormatter

class ParseEventAdapter(private val context: Context, private val events: MutableList<ParseEvent>) :
    RecyclerView.Adapter<ParseEventAdapter.ViewHolder>() {
    lateinit var currLocation: ParseGeoPoint

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false)
        ParseGeoPoint.getCurrentLocationInBackground(1) { geoPoint, _ -> currLocation = geoPoint }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun clear() {
        events.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvDayTime: TextView = itemView.findViewById(R.id.tvDayTime)
        private val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)

        fun bind(event: ParseEvent) {
            val eventLocation = event.getLocation()
            val distanceAway = eventLocation!!.distanceInMilesTo(currLocation)
            val locationText = "$distanceAway miles away"

            tvTitle.text = event.getTitle()
            tvDate.text = event.getDate().toString().format(DateTimeFormatter.ofPattern("MMM d"))
            tvDayTime.text =
                event.getDate().toString().format(DateTimeFormatter.ofPattern("E h:mma"))
            tvLocation.text = locationText
        }
    }
}