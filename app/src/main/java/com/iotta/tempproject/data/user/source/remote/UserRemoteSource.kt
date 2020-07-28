package com.iotta.tempproject.data.user.source.remote

import com.iotta.tempproject.data.user.model.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface UserRemoteSource {

    @GET("users")
    fun getUsers(): Single<List<User>>

    @GET("user/{login}")
    fun getUser(@Path("login") login: String): Single<User>

}