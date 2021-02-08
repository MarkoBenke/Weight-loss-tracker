package com.example.weightlosstracker.repository.user

import com.example.weightlosstracker.domain.User
import com.example.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: User)
    suspend fun getUser(): Flow<DataState<User?>>
    suspend fun getUsersStartDate(): Flow<Long>
}