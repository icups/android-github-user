package com.github.ext.refresh

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.ext.common.launchDelayedFunction

fun SwipeRefreshLayout.refresh() {
    isRefreshing = true
}

fun SwipeRefreshLayout.finish(delay: Long = 0) {
    launchDelayedFunction(delay) { isRefreshing = false }
}