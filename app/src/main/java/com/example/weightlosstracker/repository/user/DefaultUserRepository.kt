package com.example.weightlosstracker.repository.user

import com.example.weightlosstracker.data.local.SettingsManager
import com.example.weightlosstracker.data.local.dao.UserDAO
import com.example.weightlosstracker.data.local.dao.WeightEntryDAO
import com.example.weightlosstracker.data.local.mappers.UserMapper
import com.example.weightlosstracker.data.local.model.WeightEntryCache
import com.example.weightlosstracker.domain.User
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.parseDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultUserRepository constructor(
    private val userDAO: UserDAO,
    private val weightEntryDAO: WeightEntryDAO,
    private val settingsManager: SettingsManager,
    private val userMapper: UserMapper
) : UserRepository {

    override suspend fun insertUser(user: User) {
        userDAO.insertUserGoal(userMapper.mapToEntity(user))
        settingsManager.saveStartDate(
            parseDate(user.startDate)!!.time
        )
        weightEntryDAO.insertWeightEntry(
            WeightEntryCache(
                currentWeight = user.currentWeight,
                waistSize = user.startWaistSize,
                date = user.startDate,
                description = user.goalName
            )
        )
    }

    override suspend fun getUser(): Flow<DataState<User?>> = flow {
        emit(DataState.Loading)
        val userCache = userDAO.getUser()
        if (userCache != null) {
            emit(DataState.Success(userMapper.mapFromEntity(userCache)))
        } else {
            emit(DataState.Success(null))
        }
    }

    override suspend fun getUsersStartDate(): Flow<Long> {
        return settingsManager.getStartDate()
    }
}