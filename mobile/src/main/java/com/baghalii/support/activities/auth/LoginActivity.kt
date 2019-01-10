package com.baghalii.support.activities.auth

import android.annotation.SuppressLint
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
import com.baghalii.support.extensions.snack
import com.baghalii.support.network.Webservice
import com.baghalii.support.utilities.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

        userName.error = null
        password.error = null

        val userNameStr = userName.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        if (TextUtils.isEmpty(userNameStr)) {
            userName.error = getString(R.string.error_field_required)
            focusView = userName
            cancel = true
        } else if (!isUserNameValid(userNameStr)) {
            userName.error = getString(R.string.error_invalid_email)
            focusView = userName
            cancel = true
        }

        if (cancel) {
            focusView?.requestFocus()
        } else {
            showProgress(true)
            performLogin(userNameStr, passwordStr)
        }
    }

    @SuppressLint("CheckResult")
    private fun performLogin(userNameStr: String, passwordStr: String) {
        Webservice.create().login(LoginPostJsonModel(userNameStr, passwordStr))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    App.prefs.accessToken = result.token
                    App.prefs.isLoggedIn = true
                },
                { error ->
                    snack(error.message!!, buttonTxt = R.string.retry,
                        action = View.OnClickListener {
                            performLogin(userNameStr, passwordStr)
                        })
                }
            )
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
