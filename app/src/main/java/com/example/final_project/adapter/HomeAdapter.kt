package com.example.final_project.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.final_project.fragments.ChatFragment
import com.example.final_project.fragments.HomeFragment
import com.example.final_project.fragments.TMEventFragment

val StringArray = arrayOf("Tab1", "Tab2")

internal class MyAdapter(
//    var context: HomeFragment,
    fm: FragmentManager,
    private val fragment: Array<Fragment>
//    var totalTabs: Int
) :

FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return this.fragment[position]
    }

    override fun getCount(): Int {
        return fragment.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return StringArray[position]
    }
}