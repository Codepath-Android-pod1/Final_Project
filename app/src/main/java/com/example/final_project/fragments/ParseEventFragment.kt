package com.example.final_project.fragments

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.EndlessRecyclerViewScrollListener
import com.example.final_project.R
import com.example.final_project.adapters.ParseEventAdapter
import com.example.final_project.models.ParseEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.parse.ParseGeoPoint
import com.parse.ParseQuery
import com.parse.ParseUser
import kotlin.properties.Delegates


class ParseEventFragment : TMEventFragment() {
    lateinit var parseAdapter: ParseEventAdapter
    private var allPEvents: MutableList<ParseEvent> = mutableListOf()
    private var numResults by Delegates.notNull<Int>()

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvEvents = view.findViewById(R.id.rvEvent)
        parseAdapter = ParseEventAdapter(requireContext(), allPEvents)
        rvEvents.adapter = parseAdapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        rvEvents.layoutManager = linearLayoutManager
        val scrollListener =
            object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    loadMoreData()
                }
            }
        rvEvents.addOnScrollListener(scrollListener)

        swipeContainer = view.findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            queryEvents()
        }

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright)

        queryEvents()
    }

    override fun loadMoreData() {
        val query: ParseQuery<ParseEvent> = ParseQuery.getQuery(ParseEvent::class.java)
        val currLocation = ParseUser.getCurrentUser().getParseGeoPoint("Location")
        query.limit = 10
        query.whereNear("location", currLocation)
        query.orderByAscending("date")
        query.skip = numResults

        query.findInBackground { events, e ->
            if (e != null) {
                Log.e(TAG, "Error fetching events")
            } else {
                if (events != null) swipeContainer.isRefreshing = false
                allPEvents.addAll(events)
                numResults += events.size
                parseAdapter.notifyDataSetChanged()
            }
        }
        ParseQuery.clearAllCachedResults()
    }

    override fun queryEvents() {
        val query: ParseQuery<ParseEvent> = ParseQuery.getQuery(ParseEvent::class.java)
        val currLocation : ParseGeoPoint? = try {
            ParseUser.getCurrentUser().getParseGeoPoint("Location")
        } catch (e: Exception) {
            null
        }
        numResults = 10
        query.limit = numResults
        query.whereNear("location", currLocation)
        query.orderByAscending("date")

        parseAdapter.clear()
        query.findInBackground { events, e ->
            if (e != null) {
                Log.e(TAG, "Error fetching events")
            } else {
                if (events != null) swipeContainer.isRefreshing = false
                allPEvents.addAll(events)
                parseAdapter.notifyDataSetChanged()
            }
        }
        ParseQuery.clearAllCachedResults()
    }

    companion object {
        const val TAG = "ParseEventFragment"
    }
}