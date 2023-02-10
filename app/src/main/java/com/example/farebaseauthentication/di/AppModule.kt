package com.example.farebaseauthentication.di

import com.example.farebaseauthentication.data.AuthRepository
import com.example.farebaseauthentication.data.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesAuthRepository(firebaseAuth: FirebaseAuth) : AuthRepository{
        return AuthRepositoryImpl(firebaseAuth)
    }
}