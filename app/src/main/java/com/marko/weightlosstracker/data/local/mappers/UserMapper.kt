package com.marko.weightlosstracker.data.local.mappers

import com.marko.weightlosstracker.data.local.model.UserCache
import com.marko.weightlosstracker.data.remote.model.RemoteUser
import com.marko.weightlosstracker.model.Gender
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.data.util.EntityMapper
import com.marko.weightlosstracker.data.util.RemoteEntityMapper
import javax.inject.Inject

class UserMapper @Inject constructor() : EntityMapper<UserCache, User>,
    RemoteEntityMapper<RemoteUser, User> {

    override fun mapFromEntity(entity: UserCache): User {
        return User(
            username = entity.username,
            startWeight = entity.startWeight,
            currentWeight = entity.currentWeight,
            targetWeight = entity.targetWeight,
            startWaistSize = entity.startWaistSize,
            startBmi = entity.startBmi,
            height = entity.height,
            startDate = entity.startDate,
            age = entity.age,
            gender = Gender.valueOf(entity.gender),
            goalName = entity.goalName
        )
    }

    override fun mapToEntity(domainModel: User): UserCache {
        return UserCache(
            username = domainModel.username,
            startWeight = domainModel.startWeight,
            currentWeight = domainModel.currentWeight,
            targetWeight = domainModel.targetWeight,
            startWaistSize = domainModel.startWaistSize,
            startBmi = domainModel.startBmi,
            height = domainModel.height,
            startDate = domainModel.startDate,
            age = domainModel.age,
            gender = domainModel.gender.name,
            goalName = domainModel.goalName
        )
    }

    override fun mapFromRemoteEntity(entity: RemoteUser): User {
        return User(
            entity.id, entity.username, entity.startWeight, entity.currentWeight,
            entity.targetWeight, entity.startWaistSize, entity.startBmi, entity.height,
            entity.startDate, entity.age, Gender.valueOf(entity.gender), entity.goalName
        )
    }

    override fun mapToRemoteEntity(domainModel: User): RemoteUser {
        return RemoteUser(
            domainModel.uuid, domainModel.username, domainModel.startWeight,
            domainModel.currentWeight, domainModel.targetWeight, domainModel.startWaistSize,
            domainModel.startBmi, domainModel.height, domainModel.startDate, domainModel.age,
            domainModel.gender.name, domainModel.goalName
        )
    }

    fun remoteToLocal(remoteUser: RemoteUser): UserCache {
        return UserCache(
            username = remoteUser.username,
            startWeight = remoteUser.startWeight,
            currentWeight = remoteUser.currentWeight,
            targetWeight = remoteUser.targetWeight,
            startWaistSize = remoteUser.startWaistSize,
            startBmi = remoteUser.startBmi,
            height = remoteUser.height,
            startDate = remoteUser.startDate,
            age = remoteUser.age,
            gender = remoteUser.gender,
            goalName = remoteUser.goalName
        )
    }
}