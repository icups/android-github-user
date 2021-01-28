package com.github.app.listener

import android.view.View
import android.widget.AdapterView

abstract class OnItemSelectedListener : AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {}
    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {}
}