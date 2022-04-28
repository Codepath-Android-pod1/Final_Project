package com.example.final_project.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.final_project.EndlessRecyclerViewScrollListener
import com.example.final_project.R
import com.example.final_project.activities.MainActivity
import com.example.final_project.adapters.EventAdapter
import com.example.final_project.models.Event
import com.example.final_project.models.EventData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates


open class TMEventFragment : Fragment() {

    lateinit var rvEvents: RecyclerView
    lateinit var adapter: EventAdapter
    var allEvents: MutableList<Event> = mutableListOf()
    lateinit var swipeContainer: SwipeRefreshLayout
    lateinit var scrollListener: EndlessRecyclerViewScrollListener
    var pageNum by Delegates.notNull<Int>()
    var city: String = ""
    private var keyword: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_rv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvEvents = view.findViewById(R.id.rvEvent)
        adapter = EventAdapter(requireContext(), allEvents)
        rvEvents.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(requireContext())
        rvEvents.layoutManager = linearLayoutManager
        scrollListener =
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

//        rvEvents.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (dy > 0) {
//                    // Scrolling up
//
//                } else {
//                    // Scrolling down
//
//                }
//            }
//
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                when (newState) {
//                    AbsListView.OnScrollListener.SCROLL_STATE_FLING -> {
//                        // Do something
//                        Log.i(TAG, "1")
//                    }
//                    AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL -> {
//                        // Do something
//                        Log.i(TAG, "2")
//                    }
//                    else -> {
//                        // Do something
//                        Log.i(TAG, "3")
//                    }
//                }
//            }
//        })
    }

    override fun onStart() {
        super.onStart()
        queryEvents()
    }

    open fun loadMoreData() {
        val parameters = hashMapOf<String, String>()
        parameters["apikey"] = getString(R.string.ticketmaster_key)
        parameters["city"] = city
        parameters["keyword"] = keyword
        parameters["geoPoint"] = MainActivity.geoHash
        parameters["page"] = pageNum++.toString()

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
                    allEvents.addAll(events)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<EventData>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }


    open fun queryEvents() {
        val parameters = hashMapOf<String, String>()
        parameters["apikey"] = getString(R.string.ticketmaster_key)
        parameters["city"] = ""
        parameters["keyword"] = ""
        parameters["geoPoint"] = MainActivity.geoHash
        // Reset pageNum when events are replaced
        pageNum = 0
        parameters["page"] = pageNum.toString()

        adapter.clear()
        scrollListener.resetState()

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