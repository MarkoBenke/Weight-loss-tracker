package com.marko.weightlosstracker.repository.auth

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.marko.weightlosstracker.data.local.SettingsManager
import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class DefaultAuthRepository(
    private val settingsManager: SettingsManager,
    private val firebaseAuth: FirebaseAuth,
) : AuthRepository {

    override suspend fun signInUser(googleAuthCredential: AuthCredential): Flow<DataState<AuthResult?>> =
        flow {
            emit(DataState.Loading)
            try {
                val result = firebaseAuth.signInWithCredential(googleAuthCredential).await()
                if (result != null) {
                    settingsManager.saveUserId(result.user?.uid)
                    emit(DataState.Success(result))
                } else {
                    emit(DataState.Error())
                }
            } catch (ex: Exception) {
                emit(DataState.Error(ex.localizedMessage ?: ""))
            }
        }

    override fun isUserSignedIn(): Flow<DataState<Unit>> = flow {
        if (firebaseAuth.currentUser != null) {
            emit(DataState.Success(Unit))
        } else {
            emit(DataState.Error())
        }
    }
}