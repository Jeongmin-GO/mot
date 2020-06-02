package com.example.mot.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MenuPagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {
    private var fragments : ArrayList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    fun addItems(fragment : MenuFragment){
        fragments.add(fragment)
    }
}