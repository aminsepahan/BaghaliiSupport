package com.baghalii.support.activities.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.baghalii.support.R
import com.baghalii.support.extensions.showHideFade
import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        signInButton.setOnClickListener { attemptLogin() }
    }

    private fun attemptLogin() {

        email.error = null
        password.error = null

        val userName = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        if (TextUtils.isEmpty(userName)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isUserNameValid(userName)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        if (cancel) {
            focusView?.requestFocus()
        } else {
            showProgress(true)
            performLogin(userName, passwordStr)
        }
    }

    private fun performLogin(userName: String, passwordStr: String) {


    }

    private fun isUserNameValid(userName: String): Boolean {
        return userName.length > 4
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }


    private fun showProgress(show: Boolean) {
        login_form.showHideFade(show)
        login_progress.showHideFade(show)
    }


    companion object {
        public fun open(activity: Activity, isFromSplash: Boolean) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
