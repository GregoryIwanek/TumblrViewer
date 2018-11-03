package com.grzegorziwanek.tumblrviewer.util

import android.view.View

class ScrollTransitionAnimator {

    fun animateShow(vararg views: View) {
        for (view in views) {
            if (view.translationY == 0f) return
            view.animate().translationY(0f).setStartDelay(100).setDuration(300).start()
        }
    }

    fun animateHide(hideDirection: Int, view: View) {
        if (view.translationY != 0f) return
        val moveY = if (hideDirection == DIRECTION_BOTTOM) (2 * view.height) else -(2 * view.height)
        view.animate().translationY(moveY.toFloat()).setStartDelay(100).setDuration(300).start()
    }

    companion object {
        const val DIRECTION_TOP = 1
        const val DIRECTION_BOTTOM = 2
    }
}