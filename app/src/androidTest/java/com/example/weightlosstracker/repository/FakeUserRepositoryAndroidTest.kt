package com.example.weightlosstracker.repository

import androidx.lifecycle.MutableLiveData
import com.example.weightlosstracker.domain.User
import com.example.weightlosstracker.repository.user.UserRepository
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.parseDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeUserRepositoryAndroidTest @Inject constructor() : UserRepository {

    private val users = mutableListOf<User>()
    private val observableUsers = MutableLiveData<List<User>>(users)

    private var shouldReturnError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnError = value
    }

    private fun refreshLiveData() {
        observableUsers.postValue(users)
    }

    override suspend fun insertUser(user: User) {
        users.add(user)
        refreshLiveData()
    }

    override suspend fun getUser(): Flow<DataState<User?>> = flow {
        if (shouldReturnError) {
            emit(DataState.Error("Error!"))
            return@flow
        }
        emit(DataState.Success(users.first()))
    }

    override suspend fun getUsersStartDate(): Flow<Long> = flow {
        if (shouldReturnError) {
            emit(0L)
            return@flow
        }
        val startDate = users.first().startDate
        emit(parseDate(startDate)!!.time)
    }
}