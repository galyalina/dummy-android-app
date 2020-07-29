package com.iotta.tempproject.utils.authentication


import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val requestBuilder = chain.request().newBuilder()

        buildAuthHeader(token)
            .takeIf { chain.request().header(AUTH_HEADER) != it }
            ?.let { requestBuilder.header(AUTH_HEADER, it).build() }

        return chain.proceed(requestBuilder.build())
    }

    companion object {

        // TODO add to secure_storage
        const val token = "3c162242302aef7e7ac23949279217c7ba834d1a"
        const val AUTH_HEADER = "Authorization"
        private const val AUTH_BEARER = "Bearer"

        fun buildAuthHeader(token: String): String = "$AUTH_BEARER $token"
    }
}
