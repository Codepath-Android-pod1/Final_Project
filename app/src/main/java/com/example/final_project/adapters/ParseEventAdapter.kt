package com.example.final_project.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.activities.MainActivity
import com.example.final_project.models.ParseEvent
import com.parse.ParseGeoPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class ParseEventAdapter(private val context: Context, private val events: MutableList<ParseEvent>) :
    RecyclerView.Adapter<ParseEventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false)
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
            val currLocation = ParseGeoPoint(
                MainActivity.currLocation.latitude,
                MainActivity.currLocation.longitude
            )
            val distanceAway = eventLocation!!.distanceInMilesTo(currLocation).roundToInt()
            val locationText = "$distanceAway miles away"
            val parser = SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy", Locale.ENGLISH)

            tvTitle.text = event.getTitle()
            val date = parser.parse(event.getDate().toString()) as Date
            tvDate.text = SimpleDateFormat("MMM d", Locale.ENGLISH).format(date)
            tvDayTime.text = SimpleDateFormat("E h:mma", Locale.ENGLISH).format(date)
            tvLocation.text = locationText
        }
    }
}