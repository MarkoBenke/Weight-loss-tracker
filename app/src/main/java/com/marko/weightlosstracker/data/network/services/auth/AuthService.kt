package com.marko.weightlosstracker.data.network.services.auth

import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow

interface AuthService {

    suspend fun signInUser(token: String): Flow<DataState<Unit>>
    fun isUserSignedIn(): Boolean
}