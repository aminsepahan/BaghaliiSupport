package com.baghalii.support.activities.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import com.baghalii.support.R
import com.baghalii.support.activities.auth.LoginActivity
import com.baghalii.support.activities.main.MainActivity
import com.baghalii.support.utilities.App
import com.github.florent37.viewanimator.ViewAnimator
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.decorView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                v.removeOnLayoutChangeListener(this)
                exitReveal()
            }
        })
    }

    fun exitReveal() {
        // get the center for the clipping circle
        val cx = splashLogoBack.left + splashLogoBack.measuredWidth / 2
        val cy = splashLogoBack.top + splashLogoBack.measuredHeight / 2

        // get the initial radius for the clipping circle
        val initialRadius = animatingView.height

        // create the animation (the final radius is zero)
        val anim = ViewAnimationUtils.createCircularReveal(animatingView, cx, cy, initialRadius.toFloat(), 0f)

        anim.duration = 800
        anim.interpolator = AccelerateDecelerateInterpolator()


        // make the view invisible when the animation is done
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                animatingView.visibility = View.INVISIBLE
            }
        })
        ViewAnimator.animate(findViewById(R.id.splashLogo)).translationX(50f, 0f).fadeIn().duration(600).startDelay(300)
            .onStop { startProcess() }.decelerate().start()
        // start the animation
        anim.start()
    }

    private fun startProcess() {
        if (App.prefs.isLoggedIn) {
            openApp()
        } else {
            openAuth()
        }
    }

    private fun openAuth() {
        LoginActivity.open(this, true)
    }

    private fun openApp() {
        MainActivity.open(this, true)
    }
}
