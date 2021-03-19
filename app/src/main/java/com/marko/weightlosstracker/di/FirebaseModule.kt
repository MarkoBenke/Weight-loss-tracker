package com.marko.weightlosstracker.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marko.weightlosstracker.data.remote.FirebaseHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun provideFireStore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideFirebaseHelper(fireStore: FirebaseFirestore, auth: FirebaseAuth) =
        FirebaseHelper(fireStore, auth)
}