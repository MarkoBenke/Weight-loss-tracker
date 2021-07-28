package com.marko.weightlosstracker.repository

import com.marko.weightlosstracker.repository.auth.AuthRepository
import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeAuthRepositoryAndroidTest @Inject constructor(
    private val shouldReturnError: Boolean
) : AuthRepository {

    override suspend fun signInUser(token: String): Flow<DataState<Unit>> =
        flow {
            if (shouldReturnError) emit(DataState.Error("Error!"))
        }

    override fun isUserSignedIn(): Boolean {
        if (shouldReturnError) {
            return false
        }

        return true
    }
}