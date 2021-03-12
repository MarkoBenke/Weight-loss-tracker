package com.marko.weightlosstracker.repository.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInUser(googleAuthCredential: AuthCredential): Flow<DataState<AuthResult?>>
    fun isUserSignedIn(): Flow<DataState<Unit>>
    suspend fun logoutUser()
}