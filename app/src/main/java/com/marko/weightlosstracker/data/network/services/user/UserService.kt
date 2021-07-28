package com.marko.weightlosstracker.data.network.services.user

import com.marko.weightlosstracker.data.network.entities.UserDto

interface UserService {

    suspend fun getUser(): UserDto?
    suspend fun updateUserProfile(user: UserDto): Boolean
    suspend fun updateUserWeightData(user: UserDto): Boolean
    suspend fun insertUser(user: UserDto): Boolean
}