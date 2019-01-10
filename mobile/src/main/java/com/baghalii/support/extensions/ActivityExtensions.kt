package com.baghalii.support.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import com.baghalii.support.R

public fun View.showHideFade(showHide: Boolean){
    visibility = if (showHide) View.GONE else View.VISIBLE
    animate()
        .setDuration(resources.getDimension(R.dimen.animation_short).toLong())
        .alpha((if (showHide) 0 else 1).toFloat())
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                visibility = if (showHide) View.GONE else View.VISIBLE
            }
        })
}