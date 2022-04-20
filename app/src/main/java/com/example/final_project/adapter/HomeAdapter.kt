package com.example.final_project.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.final_project.fragments.ChatFragment
import com.example.final_project.fragments.HomeFragment
import com.example.final_project.fragments.SettingFragment

@Suppress("DEPRECATION")
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
                SettingFragment()
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}