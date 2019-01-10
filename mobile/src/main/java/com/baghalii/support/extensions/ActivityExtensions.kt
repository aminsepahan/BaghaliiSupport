package com.baghalii.support.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.support.design.widget.Snackbar
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

fun Activity.snack(message: String, buttonTxt: Int = R.string.ok,
                   action: View.OnClickListener? = null, indefinite: Boolean = false) {
    val snackbar: Snackbar = if (indefinite) {
        Snackbar.make(window.decorView.rootView, message, Snackbar.LENGTH_INDEFINITE)
    } else {
        Snackbar.make(window.decorView.rootView, message, Snackbar.LENGTH_LONG)
    }
    snackbar.setAction(buttonTxt, action).setActionTextColor(resources.getColor(R.color.md_yellow_200))
    snackbar.show()
}