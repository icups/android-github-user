package com.github.app.binding

import android.view.Gravity
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper

object RecyclerBinding {

    @JvmStatic
    @BindingAdapter("snapEnd")
    fun enableSnapEnd(recyclerView: RecyclerView?, enable: Boolean = false) {
        recyclerView?.run {
            if (enable) GravitySnapHelper(Gravity.END).attachToRecyclerView(this)
        }
    }

}