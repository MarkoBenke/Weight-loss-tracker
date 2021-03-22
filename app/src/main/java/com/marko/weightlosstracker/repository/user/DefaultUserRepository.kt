package com.marko.weightlosstracker.repository.user

import com.marko.weightlosstracker.data.local.settings.SettingsManager
import com.marko.weightlosstracker.data.local.dao.UserDao
import com.marko.weightlosstracker.data.local.dao.WeightEntryDao
import com.marko.weightlosstracker.data.local.mappers.UserMapper
import com.marko.weightlosstracker.data.local.mappers.WeightEntryMapper
import com.marko.weightlosstracker.data.remote.datasource.UserService
import com.marko.weightlosstracker.data.remote.datasource.WeightEntryService
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.data.util.UserTable
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
        val userResult = userService.insertUser(userMapper.mapToRemoteEntity(user))
        if (userResult) {
            val weightEntryResult = weightEntryService.insertWeightEntry(
                weightEntryMapper.mapToRemoteEntity(user.getInitialWeightEntry())
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
        val userMap = getUserMap(user)
        val result = userService.updateUser(userMap)
        if (result) {
            userDao.updateUser(userMapper.mapToEntity(user))
            emit(DataState.Success(Unit))
        } else emit(DataState.Error())
    }

    override suspend fun syncUserData(): Flow<Unit> = flow {
        val remoteUser = userService.getUser()
        val userCache = userDao.getUser()

        if (remoteUser != null) {
            userCache?.let {
                if (userMapper.mapFromRemoteEntity(remoteUser) != userMapper.mapFromEntity(it)) {
                    userDao.updateUser(userMapper.remoteToLocal(remoteUser))
                }
            } ?: userDao.insertUser(userMapper.remoteToLocal(remoteUser))
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

    private fun getUserMap(user: User): HashMap<String, Any?> {
        return hashMapOf(
            UserTable.TARGET_WEIGHT to user.targetWeight,
            UserTable.AGE to user.age,
            UserTable.USERNAME to user.username
        )
    }
}