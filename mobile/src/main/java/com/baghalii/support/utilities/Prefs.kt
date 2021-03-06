package com.baghalii.support.utilities

import android.content.Context
import android.content.SharedPreferences
import com.baghalii.support.utilities.Constants.ACCESS_TOKEN
import com.baghalii.support.utilities.Constants.IS_LOGGED_IN
import com.baghalii.support.utilities.Constants.NULL
import com.baghalii.support.utilities.Constants.USER

class Prefs(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(Constants.SP_FILE_NAME_BASE, 0)

    var accessToken: String
        get() = prefs.getString(ACCESS_TOKEN, NULL)!!
        set(value) = prefs.edit().putString(ACCESS_TOKEN, value).apply()

    var isLoggedIn: Boolean
        get() = prefs.getBoolean(IS_LOGGED_IN, false)
        set(value) = prefs.edit().putBoolean(IS_LOGGED_IN, value).apply()

    var user: Boolean
        get() = prefs.getBoolean(USER, false)
        set(value) = prefs.edit().putBoolean(USER, value).apply()

    fun isSet(value: String): Boolean{
        return value != NULL
    }
}