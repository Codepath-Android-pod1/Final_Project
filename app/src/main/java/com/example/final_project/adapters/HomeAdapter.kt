package com.example.final_project.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

val StringArray = arrayOf("Social", "Professional")

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