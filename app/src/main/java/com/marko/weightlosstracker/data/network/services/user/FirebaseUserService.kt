package com.marko.weightlosstracker.data.network.services.user

import com.google.firebase.firestore.ktx.toObject
import com.marko.weightlosstracker.data.network.FirebaseHelper
import com.marko.weightlosstracker.data.network.entities.UserDto
import com.marko.weightlosstracker.data.util.UserTable
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserService @Inject constructor(
    private val firebaseHelper: FirebaseHelper
) : UserService {

    override suspend fun insertUser(user: UserDto): Boolean {
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

    override suspend fun updateUserWeightData(user: UserDto): Boolean {
        return try {
            var isSuccessful = false
            val userMap = getWeightEntryUserMap(user)
            firebaseHelper.getUserDocument().update(userMap).addOnCompleteListener {
                isSuccessful = it.isSuccessful
            }.await()
            isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun updateUserProfile(user: UserDto): Boolean {
        return try {
            var isSuccessful = false
            val userMap = getProfileUserMap(user)
            firebaseHelper.getUserDocument().update(userMap).addOnCompleteListener {
                isSuccessful = it.isSuccessful
            }.await()
            isSuccessful
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getUser(): UserDto? {
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

    private fun getProfileUserMap(user: UserDto): HashMap<String, Any?> {
        return hashMapOf(
            UserTable.TARGET_WEIGHT to user.targetWeight,
            UserTable.AGE to user.age,
            UserTable.USERNAME to user.username
        )
    }

    private fun getWeightEntryUserMap(user: UserDto?): HashMap<String, Any?> {
        return hashMapOf(
            UserTable.START_WAIST_SIZE to user?.startWaistSize,
            UserTable.START_BMI to user?.startBmi,
            UserTable.START_WEIGHT to user?.startWeight
        )
    }
}