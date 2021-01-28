package com.github.app.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.github.ext.view.backgroundColor
import com.github.ext.view.gone
import com.github.ext.view.invisible
import com.github.ext.view.visible

object ViewBinding {

    @JvmStatic
    @BindingAdapter("backgroundColor")
    fun setBackgroundColor(view: View, hexCode: String? = "#ffffff") {
        hexCode?.let { view.backgroundColor(it) }
    }

    @JvmStatic
    @BindingAdapter("visibility")
    fun setVisibility(view: View, visibility: Int) {
        when (visibility) {
            View.VISIBLE -> view.visible()
            View.GONE -> view.gone()
            View.INVISIBLE -> view.invisible()
        }
    }

}