package com.github.ext.view

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.github.app.R

fun View.slideDown() {
    val anim = AnimationUtils.loadAnimation(context, R.anim.slide_down)
    startAnimation(anim)

    gone(500)
}

fun View.slideUp() {
    visible()

    val anim = AnimationUtils.loadAnimation(context, R.anim.slide_up)
    startAnimation(anim)
}

fun TextView?.textColor(hexCode: String) {
    this?.run { setTextColor(Color.parseColor(hexCode)) }
}

fun TextView?.backgroundColor(hexCode: String) {
    this?.let { setBackgroundColor(Color.parseColor(hexCode)) }
}

fun View?.backgroundColor(hexCode: String) {
    this?.run { setBackgroundColor(Color.parseColor(hexCode)) }
}

fun CardView?.backgroundColor(hexCode: String) {
    this?.run {
        setCardBackgroundColor(Color.parseColor(hexCode))
        visible(false)
    }
}

fun View?.onItemClicked() {
    this?.let {
        setOnClickListener { _ ->
            isEnabled = false
            postDelayed({ isEnabled = true }, 1000)
        }
    }
}

fun View.visible(animate: Boolean = true) {
    if (!animate) {
        visibility = View.VISIBLE
        alpha = 1.0f
        return
    }

    alpha = 0f
    visibility = View.VISIBLE
    animate().alpha(1.0f).duration = 100
}

fun View.invisible(delayMillis: Long = 100) {
    animate().alpha(0f).duration = delayMillis

    Handler().postDelayed({
        visibility = View.INVISIBLE
    }, delayMillis)
}

fun View.gone(delayMillis: Long = 100) {
    animate().alpha(0f).duration = delayMillis

    Handler().postDelayed({
        visibility = View.GONE
    }, delayMillis)
}

fun View?.toggleView(isTrue: Boolean, isGone: Boolean = true) {
    this?.let {
        if (isTrue) {
            visible()
        } else {
            if (isGone) {
                gone()
            } else {
                invisible()
            }
        }
    }
}

fun TextView.applyNormal() {
    typeface = Typeface.DEFAULT
}

fun TextView.applyBold() {
    setTypeface(null, Typeface.BOLD)
}

fun TextView.applyItalic() {
    setTypeface(null, Typeface.ITALIC)
}

fun TextView.applyBoldItalic() {
    setTypeface(null, Typeface.BOLD_ITALIC)
}

fun TextView.applyStrikeThrough() {
    paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
}