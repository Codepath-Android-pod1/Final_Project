package com.example.final_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.final_project.R
import com.example.final_project.adapter.MyAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

open class HomeFragment : Fragment() {

    lateinit var vp: ViewPager
    lateinit var tl: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vp = view.findViewById(R.id.viewpager)
        tl = view.findViewById(R.id.tablayout)

        tl.setupWithViewPager(vp)
        vp.offscreenPageLimit = 2
        tl.addTab(tl.newTab().setText("Testing 1"))
        tl.addTab(tl.newTab().setText("Testing 2"))
        tl.tabGravity = TabLayout.GRAVITY_FILL
        val fragmentArray = arrayOf(ChatFragment(), TMEventFragment())
        val adapter = MyAdapter(childFragmentManager, fragmentArray)

        vp.adapter = adapter
        vp.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tl))
        tl.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                vp.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }


}