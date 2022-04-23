package com.example.final_project.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.final_project.R
import com.example.final_project.activities.MainActivity
import com.example.final_project.adapter.EventAdapter
import com.example.final_project.models.Event
import com.example.final_project.models.EventData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TMEventFragment : Fragment() {

    lateinit var eventsRV: RecyclerView
    lateinit var adapter: EventAdapter
    var allEvents: MutableList<Event> = mutableListOf()
    lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_t_m_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventsRV = view.findViewById(R.id.rvEvent)
        adapter = EventAdapter(requireContext(), allEvents)
        eventsRV.adapter = adapter
        eventsRV.layoutManager = LinearLayoutManager(requireContext())

        swipeContainer = view.findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            queryEvents()
        }

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright)

        queryEvents()

        eventsRV.addOnScrollListener(object: RecyclerView.OnScrollListener() {

            @Override
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Scrolling up

                } else {
                    // Scrolling down

                }
            }

            @Override
            override fun onScrollStateChanged(recyclerView: RecyclerView , newState: Int) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Do something
                    Log.i(TAG, "1")
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something
                    Log.i(TAG, "2")
                } else {
                    // Do something
                    Log.i(TAG, "3")
                }
            }
        })
    }


    open fun queryEvents() {
        val parameters = hashMapOf<String, String>()
        parameters["apikey"] = getString(R.string.ticketmaster_key)
        parameters["city"] = ""
        parameters["keyword"] = ""
        parameters["geoPoint"] = MainActivity.geoHash

        adapter.clear()

        val call: Call<EventData> = MainActivity.apiService.getEvents(parameters)
        call.enqueue(object : Callback<EventData> {
            override fun onResponse(
                call: Call<EventData>,
                response: Response<EventData>
            ) {
                if (!response.isSuccessful) {
                    Log.e(TAG, "Status code ${response.code()}")
                    return
                }

                val events = response.body()?._embedded?.events

                if (events != null) {
                    swipeContainer.isRefreshing = false
                    allEvents.addAll(events)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<EventData>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }

    companion object {
        const val TAG = "TMEventFragment"
    }
}