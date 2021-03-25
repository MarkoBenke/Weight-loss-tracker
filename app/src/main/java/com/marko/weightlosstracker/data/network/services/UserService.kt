package com.marko.weightlosstracker.data.network.services

import com.google.firebase.firestore.ktx.toObject
import com.marko.weightlosstracker.data.network.FirebaseHelper
import com.marko.weightlosstracker.data.network.entities.UserDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserService @Inject constructor(
    private val firebaseHelper: FirebaseHelper
) {

    suspend fun insertUser(userDto: UserDto): Boolean {
        return try {
            var isSuccessful = false
            userDto.id = firebaseHelper.getUserId()
            firebaseHelper.getUserDocument().set(userDto).addOnCompleteListener {
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

    suspend fun getUser(): UserDto? {
        return try {
            var userDto: UserDto? = null
            firebaseHelper.getUserDocument().get().addOnCompleteListener {
                if (it.isSuccessful) {
                    userDto = it.result?.toObject<UserDto>()
                }
            }.await()
            userDto
        } catch (e: Exception) {
            null
        }
    }
}