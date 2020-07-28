package com.iotta.tempproject.ui.main.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.iotta.tempproject.data.user.model.User
import com.iotta.tempproject.domain.user.UserUseCase
import com.iotta.tempproject.utils.Data
import com.iotta.tempproject.utils.NetworkHelper
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val userUseCase: UserUseCase,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _users = MutableLiveData<Data<List<User>>>()
    val users: LiveData<Data<List<User>>>
        get() = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _users.postValue(Data.loading(null))
            if (networkHelper.isNetworkConnected()) {
                userUseCase.getUsers()
                    .subscribe({
                        _users.postValue(Data.succeeded(it))
                    }, {
                        Log.e("IottaApp", "$it")
                        _users.postValue(Data.failed("Failed to fetch users", null))
                    })
            } else _users.postValue(Data.failed("No internet connection", null))
        }
    }
}