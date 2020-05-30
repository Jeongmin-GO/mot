package com.example.mot.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MyPagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var fragments : ArrayList<FirstFragment> = ArrayList()

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    fun addItems(fragment : FirstFragment){
        fragments.add(fragment)
    }
}