package com.github.ext.pager

import com.asksira.loopingviewpager.LoopingViewPager

fun LoopingViewPager.nextPage() {
    if (!isLastPage()) currentItem += 1
}

fun LoopingViewPager.isLastPage(): Boolean {
    return (currentItem + 1) == adapter?.count
}
