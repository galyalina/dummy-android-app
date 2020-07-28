package com.iotta.tempproject.utils

data class Data<out T>(val status: DataStatus, val data: T?, val message: String?) {

    companion object {
        fun <T> succeeded(data: T?): Data<T> {
            return Data(DataStatus.Succeeded, data, null)
        }

        fun <T> failed(msg: String, data: T?): Data<T> {
            return Data(DataStatus.Failed, data, msg)
        }

        fun <T> loading(data: T?): Data<T> {
            return Data(DataStatus.Loading, data, null)
        }
    }

    enum class DataStatus {
        Succeeded,
        Failed,
        Loading
    }
}