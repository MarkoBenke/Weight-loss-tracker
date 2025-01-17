package com.marko.weightlosstracker.repository.auth

import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInUser(token: String): Flow<DataState<Unit>>
    fun isUserSignedIn(): Boolean
}