package com.baghalii.support.activities.auth

import com.squareup.moshi.Json

data class userSupportModel(@Json(name = "_id")val id: String, val support_user: String)