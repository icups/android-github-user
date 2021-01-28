package com.github.app.binding

import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.github.ext.view.*


object TextBinding {

    @JvmStatic
    @BindingAdapter("bindText")
    fun bindText(textView: TextView, data: String?) {
        if (data.isNullOrEmpty()) textView.gone()
        else {
            textView.apply {
                text = data
                visible(false)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("bindTextColor")
    fun bindTextColor(textView: TextView, hexCode: String? = "#2c3e50") {
        if (hexCode == null) textView.textColor("#808080")
        else textView.textColor(hexCode)
    }

    @JvmStatic
    @BindingAdapter("italic")
    fun setItalicText(textView: TextView, enabled: String? = "N") {
        if (enabled == "Y") textView.applyItalic()
    }

    @JvmStatic
    @BindingAdapter("bold")
    fun setBoldText(textView: TextView, enabled: String? = "N") {
        if (enabled == "Y") textView.applyBold()
    }

    @JvmStatic
    @BindingAdapter("strikeThrough")
    fun setStrikeThrough(textView: TextView, enabled: Boolean = false) {
        if (enabled) textView.applyStrikeThrough()
    }

    @JvmStatic
    @BindingAdapter("htmlText")
    fun setHtmlText(textView: TextView, data: String?) {
        textView.text = data?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) }
    }

}