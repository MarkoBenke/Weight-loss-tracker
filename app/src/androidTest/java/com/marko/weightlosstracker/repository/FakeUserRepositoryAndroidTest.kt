package com.marko.weightlosstracker.repository

import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.repository.user.UserRepository
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.parseDate
import com.marko.weightlosstracker.utils.DataGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeUserRepositoryAndroidTest @Inject constructor(
    private val shouldReturnError: Boolean
) : UserRepository {

    override suspend fun insertUser(user: User): Flow<DataState<Unit>> = flow {
        if (shouldReturnError) {
            emit(DataState.Error("Error!"))
            return@flow
        }
        emit(DataState.Success(Unit))
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

    override suspend fun updateUser(user: User): Flow<DataState<Unit>> = flow {
        if (shouldReturnError) {
            emit(DataState.Error("Error!"))
            return@flow
        }
        emit(DataState.Success(Unit))
    }

    override suspend fun syncUserData(): Flow<Unit> = flow {
        emit(Unit)
    }

    override suspend fun getUsername(): Flow<String> = flow {
        emit(DataGenerator.user.username)
    }
}