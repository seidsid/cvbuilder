package com.example.resumebuilder

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyViewAdapter (fm: FragmentManager, lc: Lifecycle) : FragmentStateAdapter (fm,lc) {
    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> About()
            1 -> Experience()
            2 -> Contacts()
            3 -> PdfFragment()
            else -> Fragment()
        }
    }
}