package com.github.ext.vm

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> observe(owner: LifecycleOwner, data: LiveData<T>, function: (data: T) -> Unit) {
    data.observe(owner, Observer {
        function(it)
    })
}

fun <T> AppCompatActivity.observe(data: LiveData<T>, function: (data: T) -> Unit) {
    data.observe(this@observe, {
        function(it)
    })
}

fun <T> Fragment.observe(data: LiveData<T>, function: (data: T) -> Unit) {
    viewLifecycleOwner.apply {
        data.observe(this, Observer {
            function(it)
        })
    }
}