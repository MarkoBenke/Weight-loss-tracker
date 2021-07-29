package com.marko.weightlosstracker.repository.user

import com.marko.weightlosstracker.data.local.dao.UserDao
import com.marko.weightlosstracker.data.local.dao.WeightEntryDao
import com.marko.weightlosstracker.data.local.settings.SettingsManager
import com.marko.weightlosstracker.data.network.services.user.UserService
import com.marko.weightlosstracker.data.network.services.weightentry.WeightEntryService
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.model.mappers.UserMapper
import com.marko.weightlosstracker.model.mappers.WeightEntryMapper
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.parseDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultUserRepository constructor(
    private val userDao: UserDao,
    private val userService: UserService,
    private val weightEntryDao: WeightEntryDao,
    private val weightEntryService: WeightEntryService,
    private val settingsManager: SettingsManager,
    private val userMapper: UserMapper,
    private val weightEntryMapper: WeightEntryMapper
) : UserRepository {

    override suspend fun insertUser(user: User): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        val userResult = userService.insertUser(userMapper.mapToDto(user))
        if (userResult) {
            val weightEntryResult = weightEntryService.insertWeightEntry(
                weightEntryMapper.mapToDto(user.getInitialWeightEntry())
            )
            if (weightEntryResult) {
                userDao.insertUser(userMapper.mapToEntity(user))
                weightEntryDao.insertWeightEntry(
                    weightEntryMapper.mapToEntity(user.getInitialWeightEntry())
                )
                settingsManager.saveStartDate(parseDate(user.startDate)!!.time)
                emit(DataState.Success(Unit))
            } else emit(DataState.Error())
        } else emit(DataState.Error())
    }

    override suspend fun updateUser(user: User): Flow<DataState<Unit>> = flow {
        emit(DataState.Loading)
        val result = userService.updateUserProfile(userMapper.mapToDto(user))
        if (result) {
            userDao.updateUser(userMapper.mapToEntity(user))
            emit(DataState.Success(Unit))
        } else emit(DataState.Error())
    }

    override suspend fun syncUserData(): Flow<Unit> = flow {
        val remoteUser = userService.getUser()
        val userCache = userDao.getUser()

        if (remoteUser != null) {
            settingsManager.saveStartDate(parseDate(remoteUser.startDate)!!.time)
            if (userCache != null) {
                if (userMapper.mapFromDto(remoteUser) != userMapper.mapFromEntity(userCache)) {
                    userDao.updateUser(userMapper.dtoToEntity(remoteUser))
                }
            } else {
                userDao.insertUser(userMapper.dtoToEntity(remoteUser))
            }
        }
        emit(Unit)
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

    override suspend fun getUsername(): Flow<String> = flow {
        val user = userDao.getUser()
        user?.let {
            emit(it.username)
        } ?: emit("")
    }
}