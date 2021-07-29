package com.marko.weightlosstracker.repository.auth

import com.marko.weightlosstracker.data.network.services.auth.AuthService
import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow

class DefaultAuthRepository(
    private val authService: AuthService
) : AuthRepository {

    override suspend fun signInUser(token: String): Flow<DataState<Unit>> =
        authService.signInUser(token)

    override fun isUserSignedIn(): Boolean = authService.isUserSignedIn()
}