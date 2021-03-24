package com.marko.weightlosstracker.data.network.services

import com.google.firebase.firestore.ktx.toObject
import com.marko.weightlosstracker.data.network.FirebaseHelper
import com.marko.weightlosstracker.data.network.entities.RemoteUser
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

    suspend fun getUser(): RemoteUser? {
        return try {
            var user: RemoteUser? = null
            firebaseHelper.getUserDocument().get().addOnCompleteListener {
                if (it.isSuccessful) {
                    user = it.result?.toObject<RemoteUser>()
                }
            }.await()
            user
        } catch (e: Exception) {
            null
        }
    }
}