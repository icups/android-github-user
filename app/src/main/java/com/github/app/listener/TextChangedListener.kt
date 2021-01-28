package com.github.app.listener

import android.text.Editable
import android.text.TextWatcher

abstract class TextChangedListener : TextWatcher {
    override fun afterTextChanged(editable: Editable) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}