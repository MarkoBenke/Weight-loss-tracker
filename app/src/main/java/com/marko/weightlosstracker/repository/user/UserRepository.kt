package com.marko.weightlosstracker.repository.user

import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: User): Flow<DataState<Unit>>
    suspend fun getUser(): Flow<DataState<User?>>
    suspend fun getUsersStartDate(): Flow<Long>
    suspend fun updateUser(user: User): Flow<DataState<Unit>>
}