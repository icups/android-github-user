package com.github.app.binding

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import androidx.databinding.BindingAdapter
import com.github.ext.common.launchDelayedFunction
import com.github.ext.type.orFalse
import com.github.ext.type.orZero
import com.github.ext.view.gone
import com.github.ext.view.hide
import com.github.ext.view.reveal

object ViewBinding {

    @JvmStatic
    @BindingAdapter("android:isEnable")
    fun View.enabled(enable: Boolean = true) {
        isEnabled = enable
    }

    @JvmStatic
    @BindingAdapter("android:backgroundColor")
    fun View.bindBackgroundColor(hex: String? = null) {
        context?.run {
            if (hex != null) setBackgroundColor(Color.parseColor(hex))
        }
    }

    @JvmStatic
    @BindingAdapter("android:backgroundTintColor")
    fun View.bindBackgroundTintColor(hex: String? = null) {
        context?.run {
            if (hex != null) backgroundTintList = ColorStateList.valueOf(Color.parseColor(hex))
        }
    }

    @JvmStatic
    @BindingAdapter("android:animatedVisibility", "android:invisible", "android:delayVisibility", requireAll = false)
    fun View.animatedVisibility(isVisible: Boolean = true, isInvisible: Boolean? = null, delay: Long? = null) {
        launchDelayedFunction(delay.orZero()) {
            if (isVisible) reveal()
            else {
                if (isInvisible.orFalse()) hide() else gone()
            }
        }
    }

}