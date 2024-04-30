package com.yes.musicplayer.presentation.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentFactory
import androidx.viewpager2.adapter.FragmentStateAdapter

class UniversalFragmentAdapter<T : Fragment>(
    private val fragmentActivity: FragmentActivity,
    private val fragmentList: List<Class<out T>>,
    private val fragmentFactory: FragmentFactory
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentFactory.instantiate(
            fragmentActivity.classLoader,
            fragmentList[position].name
        )
    }
}