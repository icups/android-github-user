package com.github.app.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.github.ext.image.loadImage
import com.github.ext.image.loadQr

object ImageBinding {

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImageFromUrl(view: ImageView?, url: String?) {
        view?.loadImage(url)
    }

    @JvmStatic
    @BindingAdapter("loadQrCode")
    fun loadQrCode(view: ImageView?, data: String?) {
        view?.loadQr(data)
    }

}