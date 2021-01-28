package com.github.ext.alert

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Activity.showSnackBar(message: String?) {
    window.decorView.rootView?.apply { message?.let { Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show() } }
}

fun Fragment.showSnackBar(message: String?) {
    message?.let { requireActivity().showSnackBar(message) }
}

fun Activity.showLongSnackBar(message: String?) {
    window.decorView.rootView?.apply { message?.let { Snackbar.make(this, message, Snackbar.LENGTH_LONG).show() } }
}

fun Fragment.showLongSnackBar(message: String?) {
    message?.let { requireActivity().showLongSnackBar(it) }
}

fun Context.showToast(message: String?) {
    message?.let { Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }
}

fun Context.showLongToast(message: String?) {
    message?.let { Toast.makeText(this, message, Toast.LENGTH_LONG).show() }
}

fun Fragment.showToast(message: String?) {
    message?.let { requireContext().showToast(message) }
}

fun Fragment.showLongToast(message: String?) {
    message?.let { requireContext().showLongToast(message) }
}