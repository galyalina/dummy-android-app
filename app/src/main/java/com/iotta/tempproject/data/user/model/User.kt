package com.iotta.tempproject.data.user.model

import com.squareup.moshi.Json


data class User(
    @Json(name = "login")
    val login: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "email")
    val email: String = "",
    @Json(name = "avatar_url")
    val avatar: String = ""
)