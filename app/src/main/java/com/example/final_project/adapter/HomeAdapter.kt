package com.example.final_project.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.final_project.fragments.ChatFragment
import com.example.final_project.fragments.HomeFragment
import com.example.final_project.fragments.SettingFragment
import com.example.final_project.fragments.TMEventFragment

@Suppress("DEPRECATION")
val StringArray = arrayOf("Tab1", "Tab2")
internal class MyAdapter(
    var context: HomeFragment,
    fm: FragmentManager,
    var totalTabs: Int
) :

    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ChatFragment()
            }
            1 -> {
                TMEventFragment()
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return StringArray[position]
    }
}