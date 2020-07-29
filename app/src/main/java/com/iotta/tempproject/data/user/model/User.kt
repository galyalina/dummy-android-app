package com.iotta.tempproject.data.user.model

import com.squareup.moshi.Json


data class User(
    @field:Json(name = "login")
    val login: String = "",
    @field:Json(name = "id")
    val id: Int = 0,
    @field:Json(name = "name")
    val name: String = "",
    @field:Json(name = "email")
    val email: String = "",
    @field:Json(name = "avatar_url")
    val imageUrl: String = ""
)
