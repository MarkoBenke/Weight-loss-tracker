package com.example.weightlosstracker.repository

import com.example.weightlosstracker.domain.User
import com.example.weightlosstracker.repository.user.UserRepository
import com.example.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserRepository: UserRepository {

    override suspend fun insertUser(user: User) {
        //todo
    }

    override suspend fun getUser(): Flow<DataState<User?>> = flow {
        emit(DataState.Success(User()))

    }

    override suspend fun getUsersStartDate(): Flow<Long> = flow {
        emit(0L)
    }
}