package com.example.weightlosstracker.repository.user

import com.example.weightlosstracker.data.local.SettingsManager
import com.example.weightlosstracker.data.local.dao.UserDao
import com.example.weightlosstracker.data.local.dao.WeightEntryDao
import com.example.weightlosstracker.data.local.mappers.UserMapper
import com.example.weightlosstracker.data.local.model.WeightEntryCache
import com.example.weightlosstracker.domain.User
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.parseDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultUserRepository constructor(
    private val userDao: UserDao,
    private val weightEntryDao: WeightEntryDao,
    private val settingsManager: SettingsManager,
    private val userMapper: UserMapper
) : UserRepository {

    override suspend fun insertUser(user: User) {
        userDao.insertUser(userMapper.mapToEntity(user))
        settingsManager.saveStartDate(
            parseDate(user.startDate)!!.time
        )
        weightEntryDao.insertWeightEntry(
            WeightEntryCache(
                uuid = user.startDate.replace(".", ""),
                currentWeight = user.currentWeight,
                waistSize = user.startWaistSize,
                date = user.startDate,
                description = user.goalName
            )
        )
    }

    override suspend fun getUser(): Flow<DataState<User?>> = flow {
        emit(DataState.Loading)
        val userCache = userDao.getUser()
        if (userCache != null) {
            emit(DataState.Success(userMapper.mapFromEntity(userCache)))
        } else {
            emit(DataState.Error())
        }
    }

    override suspend fun getUsersStartDate(): Flow<Long> {
        return settingsManager.getStartDate()
    }
}