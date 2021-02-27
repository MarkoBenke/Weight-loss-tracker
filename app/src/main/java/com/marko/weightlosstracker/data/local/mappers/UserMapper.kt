package com.marko.weightlosstracker.data.local.mappers

import com.marko.weightlosstracker.data.local.model.UserCache
import com.marko.weightlosstracker.domain.Gender
import com.marko.weightlosstracker.domain.User
import com.marko.weightlosstracker.util.EntityMapper
import javax.inject.Inject

class UserMapper @Inject constructor() : EntityMapper<UserCache, User> {

    override fun mapFromEntity(entity: UserCache): User {
        return User(
            entity.uuid, entity.startWeight, entity.currentWeight, entity.targetWeight,
            entity.startWaistSize, entity.startBmi, entity.height, entity.startDate, entity.age,
            Gender.valueOf(entity.gender), entity.goalName
        )
    }

    override fun mapToEntity(domainModel: User): UserCache {
        return UserCache(
            domainModel.uuid, domainModel.startWeight, domainModel.currentWeight,
            domainModel.targetWeight, domainModel.startWaistSize, domainModel.startBmi,
            domainModel.height, domainModel.startDate, domainModel.age, domainModel.gender.name,
            domainModel.goalName
        )
    }
}