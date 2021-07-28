package com.marko.weightlosstracker.data.network.services.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.marko.weightlosstracker.data.network.services.auth.AuthService
import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthService {

    override suspend fun signInUser(token: String): Flow<DataState<Unit>> =
        flow {
            emit(DataState.Loading)
            val credential = GoogleAuthProvider.getCredential(token, null)
            try {
                val result = firebaseAuth.signInWithCredential(credential).await()
                if (result != null) {
                    emit(DataState.Success(Unit))
                } else {
                    emit(DataState.Error())
                }
            } catch (ex: Exception) {
                emit(DataState.Error())
            }
        }

    override fun isUserSignedIn(): Boolean = firebaseAuth.currentUser != null
}