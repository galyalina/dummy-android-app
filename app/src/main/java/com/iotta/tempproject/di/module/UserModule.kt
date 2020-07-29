package com.iotta.tempproject.di.module

import com.iotta.tempproject.data.user.UserRepository
import com.iotta.tempproject.data.user.source.remote.UserRemoteSource
import com.iotta.tempproject.domain.user.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class UserModule {

    @Provides
    @Singleton
    fun provideUserRemoteSource(@Named(NetworkModule.RETROFIT_OAUTH) retrofit: Retrofit): UserRemoteSource =
        retrofit.create(UserRemoteSource::class.java)

    @Provides
    @Singleton
    fun provideUserRepository(remoteSource: UserRemoteSource): UserRepository =
        UserRepository.Imp(remoteSource)

    @Provides
    @Singleton
    fun provideUserUseCase(repo: UserRepository): UserUseCase =
        UserUseCase.Imp(repo)

}
