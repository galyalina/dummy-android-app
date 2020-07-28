package com.iotta.tempproject.data.user

import android.util.Log
import com.iotta.tempproject.data.user.model.User
import com.iotta.tempproject.data.user.source.remote.UserRemoteSource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


interface UserRepository {

    fun getUsers(): Single<List<User>>

    class Imp @Inject constructor(private val remote: UserRemoteSource) : UserRepository {

        override fun getUsers(): Single<List<User>> =
            remote.getUsers()
                .flattenAsObservable { it }
                .flatMapSingle { user ->
                    remote.getUser(user.login)
                        .onErrorResumeNext {
                            Single.just(user)
                        }
                        .doOnSuccess {
                            Log.d("IottaApp", "$it")
                        }
                }
                .toList()
                .subscribeOn(Schedulers.computation())
    }

}
