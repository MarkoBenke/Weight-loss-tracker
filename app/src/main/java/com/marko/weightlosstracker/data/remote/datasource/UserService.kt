package com.marko.weightlosstracker.data.remote.datasource

import com.marko.weightlosstracker.data.remote.FirebaseHelper
import com.marko.weightlosstracker.data.remote.model.RemoteUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserService @Inject constructor(
    private val firebaseHelper: FirebaseHelper
) {

    suspend fun insertUser(user: RemoteUser): Boolean {
        return try {
            var isSuccessful = false
            user.id = firebaseHelper.getUserId()
            firebaseHelper.getUserDocument().set(user).addOnCompleteListener {
                isSuccessful = it.isSuccessful
            }.await()
            isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    suspend fun updateUser(userMap: HashMap<String, Any?>): Boolean {
        return try {
            var isSuccessful = false
            firebaseHelper.getUserDocument().update(userMap).addOnCompleteListener {
                isSuccessful = it.isSuccessful
            }.await()
            isSuccessful
        } catch (e: Exception) {
            false
        }

    }

    suspend fun getUser(): RemoteUser {
        TODO("Not yet implemented")
    }
}