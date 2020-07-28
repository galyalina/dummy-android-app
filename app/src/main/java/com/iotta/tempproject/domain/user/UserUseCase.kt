package com.iotta.tempproject.domain.user

import com.iotta.tempproject.data.user.UserRepository
import com.iotta.tempproject.data.user.model.User
import io.reactivex.Single
import javax.inject.Inject

interface UserUseCase {

    fun getUsers(): Single<List<User>>

    class Imp @Inject constructor(private val repository: UserRepository) : UserUseCase {

        override fun getUsers(): Single<List<User>> = repository.getUsers()

    }
}
