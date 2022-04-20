package com.example.final_project.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.example.final_project.R
import com.example.final_project.activities.MainActivity
import com.example.final_project.adapter.EventAdapter
import com.example.final_project.models.Event
import com.example.final_project.models.EventData
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class HomeFragment : Fragment() {

    lateinit var vp: ViewPager
    lateinit var tl: TabLayout
    lateinit var toolbar: Toolbar
    lateinit var eventsRV: RecyclerView
    lateinit var adapter: EventAdapter
    var allEvents: MutableList<Event> = mutableListOf()
    lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        vp = view.findViewById<ViewPager>(R.id.viewpager)
//        tl = view.findViewById<TabLayout>(R.id.tablayout)
//
//        tl.setupWithViewPager(vp)
//        tl.addTab(tl.newTab().setText("Testing 1"))
//        tl.addTab(tl.newTab().setText("Testing 2"))
//        tl.tabGravity = TabLayout.GRAVITY_FILL
//        val adapter = MyAdapter(this, parentFragmentManager, tl.tabCount)
//        vp.adapter = adapter
//        vp.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tl))
//        tl.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                vp.currentItem = tab.position
//            }
//            override fun onTabUnselected(tab: TabLayout.Tab) {}
//            override fun onTabReselected(tab: TabLayout.Tab) {}
//        })
//    }

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
    }

    private fun queryEvents() {
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
        const val TAG = "HomeFragment"
    }

}