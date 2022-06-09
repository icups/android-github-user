package com.github.app.binding

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.github.ext.image.loadCircleImage
import com.github.ext.image.loadImage

object ImageBinding {

    @JvmStatic
    @BindingAdapter("android:imageSource")
    fun AppCompatImageView.bindImageSource(url: String? = null) {
        if (!url.isNullOrEmpty()) loadImage(url)
    }

    @JvmStatic
    @BindingAdapter("android:circleImage")
    fun AppCompatImageView.bindCircleImage(url: String? = null) {
        if (!url.isNullOrEmpty()) loadCircleImage(url)
    }

}