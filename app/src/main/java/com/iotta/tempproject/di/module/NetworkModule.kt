package com.iotta.tempproject.di.module

import com.iotta.tempproject.data.user.UserRepository
import com.iotta.tempproject.data.user.source.remote.UserRemoteSource
import com.iotta.tempproject.mvvm.BuildConfig
import com.iotta.tempproject.utils.authentication.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @Named(OKHTTP_NO_AUTH)
    fun provideOkHttpClient() =
        (if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor()
                    .apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        } else OkHttpClient
            .Builder())
            .build()

    @Singleton
    @Provides
    fun provideAuthorizationInterceptor(): AuthInterceptor = AuthInterceptor()

    @Singleton
    @Provides
    @Named(OKHTTP_OAUTH)
    fun provideOkHttpClientOauth(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(authInterceptor)
            .readTimeout(120L, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    @Named(RETROFIT_NO_AUTH)
    fun provideRetrofit(
        @Named(OKHTTP_NO_AUTH) okHttpClient: OkHttpClient,
        url: String
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named(RETROFIT_OAUTH)
    fun provideRetrofitOauth(
        @Named(OKHTTP_OAUTH) okHttpClient: OkHttpClient,
        url: String
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


    companion object {
        internal const val OKHTTP_NO_AUTH = "OKHTTP_NO_AUTH"
        internal const val OKHTTP_OAUTH = "OKHTTP_OAUTH"

        const val RETROFIT_NO_AUTH = "RETROFIT_NO_AUTH"
        const val RETROFIT_OAUTH = "RETROFIT_OAUTH"
    }
}