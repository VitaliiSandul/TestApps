package com.bignerdranch.android.pecodetestapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CustomAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private val fragList: ArrayList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return fragList[position]
    }

    override fun getCount(): Int {
        return fragList.size
    }

    fun addFrag(fragment: Fragment) {
        fragList.add(fragment)
    }

    fun removeFrag() {
        if(fragList.size > 1){
            fragList.removeAt(fragList.size - 1)
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }
}