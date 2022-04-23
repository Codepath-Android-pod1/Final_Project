package com.example.final_project.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.final_project.R
import com.example.final_project.adapters.EventAdapter
import com.example.final_project.adapters.ParseEventAdapter
import com.example.final_project.models.ParseEvent
import com.parse.ParseQuery

class ParseEventFragment : TMEventFragment() {
    lateinit var parseAdapter: ParseEventAdapter
    var allPEvents: MutableList<ParseEvent> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        eventsRV = view.findViewById(R.id.rvEvent)
        parseAdapter = ParseEventAdapter(requireContext(), allPEvents)
        eventsRV.adapter = parseAdapter
        eventsRV.layoutManager = LinearLayoutManager(requireContext())

        swipeContainer = view.findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            queryEvents()
        }

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright)
    }

    override fun queryEvents() {
        val query: ParseQuery<ParseEvent> = ParseQuery.getQuery(ParseEvent::class.java)
        query.addDescendingOrder("createdAt")
        query.limit = 20

        adapter.clear()
        query.findInBackground { events, e ->
            if (e != null) {
                Log.e(TAG, "Error fetching posts")
            } else {
                if (events != null) {
                    swipeContainer.isRefreshing = false
                    for (event in events) {
                        Log.i(
                            TAG,
                            "Post: ${event.getDescription()}, username: ${event.getUser()?.username}"
                        )
                    }
                    allPEvents.addAll(events)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    companion object {
        const val TAG = "ParseEventFragment"
    }
}