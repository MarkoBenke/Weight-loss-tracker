package com.marko.weightlosstracker.repository

import com.marko.weightlosstracker.other.DataGenerator
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.repository.user.UserRepository
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.parseDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeUserRepositoryTest(private val shouldReturnError: Boolean = false) : UserRepository {

    override suspend fun insertUser(user: User) {

    }

    override suspend fun getUser(): Flow<DataState<User?>> = flow {
        if (shouldReturnError) {
            emit(DataState.Error("Error!"))
            return@flow
        }
        emit(DataState.Success(DataGenerator.user))
    }

    override suspend fun getUsersStartDate(): Flow<Long> = flow {
        if (shouldReturnError) {
            emit(0L)
            return@flow
        }
        val startDate = DataGenerator.user.startDate
        emit(parseDate(startDate)!!.time)
    }

    override suspend fun updateUser(user: User) {

    }
}