package com.example.final_project.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.final_project.R
import com.example.final_project.activities.DetailEvent
import com.example.final_project.models.Event
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class EventAdapter(private val context: Context, private val events: MutableList<Event>) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>() {

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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
//        private val ivImage: ImageView = itemView.findViewById(R.id.ivImage)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvDayTime: TextView = itemView.findViewById(R.id.tvDayTime)
        private val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(event: Event) {
            val venue = event._embedded.venues[0]
            val location = "${venue.name} - ${venue.city.name}, ${venue.state.name}"
            var date: LocalDate? = null
            if (event.dates.start.localDate != null) {
                date = LocalDate.parse(event.dates.start.localDate)
            }
            var dayTime: OffsetDateTime? = null
            if (event.dates.start.dateTime != null) {
                dayTime = OffsetDateTime.parse(event.dates.start.dateTime)
            }

            tvTitle.text = event.name
            tvDate.text =
                if (date == null) "TBA" else date.format(DateTimeFormatter.ofPattern("MMM d"))
            tvDayTime.text =
                if (dayTime == null) "" else dayTime.format(DateTimeFormatter.ofPattern("E h:mma"))
            tvLocation.text = location
//            Glide.with(itemView.context).load(event.images[0].url).into(ivImage)
        }

        override fun onClick(v: View?) {
            val event = events[absoluteAdapterPosition]
            val intent = Intent(context, DetailEvent::class.java)
            intent.putExtra(EVENT_EXTRA_DATES, event.dates)
            intent.putExtra(EVENT_EXTRA_IMAGE, event.images[0])
            intent.putExtra(EVENT_EXTRA_VENUE, event._embedded.venues[0])
            intent.putExtra(EVENT_EXTRA_NAME, event.name)
            val titlePair = Pair.create(tvTitle as View, "title")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity,
                titlePair,
            )
            context.startActivity(intent, options.toBundle())
        }
    }

    companion object {
        const val EVENT_EXTRA_NAME = "EVENT_EXTRA_NAME"
        const val EVENT_EXTRA_DATES = "EVENT_EXTRA_DATES"
        const val EVENT_EXTRA_VENUE = "EVENT_EXTRA_VENUE"
        const val EVENT_EXTRA_IMAGE = "EVENT_EXTRA_IMAGE"
    }
}