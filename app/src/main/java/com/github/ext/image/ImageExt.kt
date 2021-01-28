package com.github.ext.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.github.app.R
import com.github.ext.log.logError
import com.google.zxing.WriterException
import de.hdodenhof.circleimageview.CircleImageView
import java.net.HttpURLConnection
import java.net.URL

fun ImageView.loadImage(url: String?, placeholder: Boolean = true, request: RequestListener<Bitmap?>? = null) {
    if (url != null && url.isEmpty()) return
    Glide.with(this)
        .asBitmap()
        .apply(RequestOptions().fitCenter())
        .skipMemoryCache(false)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .dontTransform()
        .placeholder(if (placeholder) R.drawable.ic_svg_github else R.color.colorTransparent)
        .load(url)
        .listener(request)
        .error(R.mipmap.ic_launcher)
        .into(this)
}

fun ImageView.loadBanner(url: String?, request: RequestListener<Bitmap>? = null) {
    if (url != null && url.isEmpty()) return
    Glide.with(this)
        .asBitmap()
        .apply(RequestOptions().fitCenter())
        .skipMemoryCache(false)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .dontTransform()
        .load(url)
        .listener(request)
        .into(this)
}

fun ImageView.loadFromLocal(resourcesId: Int) {
    Glide.with(this)
        .asBitmap()
        .apply(RequestOptions().fitCenter())
        .skipMemoryCache(false)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .dontTransform()
        .load(resourcesId)
        .error(R.mipmap.ic_launcher)
        .into(this)
}

fun getBitmapFromURL(imageUrl: String?): Bitmap? {
    var bitmap: Bitmap? = null

    try {
        val url = URL(imageUrl)
        val connection = url.openConnection() as HttpURLConnection

        connection.setDoInput(true)
        connection.connect()

        val input = connection.getInputStream()
        bitmap = BitmapFactory.decodeStream(input)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return bitmap
}

fun CircleImageView?.loadCircleView(url: String?) {
    if (this == null || url != null && url.isEmpty()) return

    Glide.with(this)
        .load(url)
        .into(this)
}

fun ImageView?.loadQr(data: String?) {
    if (this == null || data == null) return
    val qrgEncoder = QRGEncoder(data, null, QRGContents.Type.TEXT, 300)

    try {
        val bitmap: Bitmap = qrgEncoder.encodeAsBitmap()
        setImageBitmap(bitmap)
    } catch (e: WriterException) {
        logError("loadQr > ${e.message}")
    }
}