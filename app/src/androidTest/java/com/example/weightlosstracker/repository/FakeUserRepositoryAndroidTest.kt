package com.example.weightlosstracker.repository

import com.example.weightlosstracker.domain.User
import com.example.weightlosstracker.repository.user.UserRepository
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.parseDate
import com.example.weightlosstracker.utils.DataGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeUserRepositoryAndroidTest @Inject constructor(
    private val shouldReturnError: Boolean
) : UserRepository {

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