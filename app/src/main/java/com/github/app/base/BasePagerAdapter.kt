package com.github.app.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

abstract class BasePagerAdapter<T>(manager: FragmentManager) : FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titles: MutableList<String> = ArrayList()
    private val list: MutableList<T> = ArrayList()

    override fun getPageTitle(position: Int): CharSequence? {
        if (titles.isEmpty()) {
            return ""
        }

        return titles[position]
    }

    override fun getItem(position: Int): Fragment {
        return getFragment(position)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    fun getAll(): List<T> {
        return list
    }

    fun getData(position: Int): T {
        return list[position]
    }

    fun initializeTitles(data: List<String>) {
        titles.clear()
        titles.addAll(data)

        notifyDataSetChanged()
    }

    fun initializePagerData(data: T) {
        list.add(data)
        notifyDataSetChanged()
    }

    fun initializePagerData(data: List<T>) {
        list.clear()
        list.addAll(data)

        notifyDataSetChanged()
    }

    fun replace(data: List<T>) {
        list.clear()
        list.addAll(data)

        notifyDataSetChanged()
    }

    protected abstract fun getFragment(position: Int): Fragment

    protected open fun getBundle(position: Int): Bundle {
        return Bundle()
    }

    // __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __

    private var currentPosition = 0

    fun setCurrentPosition(position: Int) {
        this.currentPosition = position

        notifyDataSetChanged()
    }

    // __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __ __

}