package com.baghalii.support.utilities

import android.content.Context
import android.content.SharedPreferences
import com.baghalii.support.utilities.Constants.ACCESS_TOKEN
import com.baghalii.support.utilities.Constants.NULL

class Prefs(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(Constants.SP_FILE_NAME_BASE, 0);

    var accessToken: String
        get() = prefs.getString(ACCESS_TOKEN, NULL)!!
        set(value) = prefs.edit().putString(ACCESS_TOKEN, value).apply()

    public fun isSet(value: String): Boolean{
        return value != NULL
    }
}