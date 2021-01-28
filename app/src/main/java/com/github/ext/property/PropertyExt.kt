package com.github.ext.property

import androidx.fragment.app.Fragment

val Fragment?.get: Fragment get() = this ?: Fragment()

val String?.get: String get() = this ?: ""

val Boolean?.get: Boolean get() = this ?: false

val Int?.get: Int get() = this ?: 0

val Float?.get: Float get() = this ?: 0f