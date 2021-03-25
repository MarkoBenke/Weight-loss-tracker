package com.marko.weightlosstracker.model.mappers

import com.marko.weightlosstracker.data.local.entities.UserEntity
import com.marko.weightlosstracker.data.network.entities.UserDto
import com.marko.weightlosstracker.data.util.DomainMapper
import com.marko.weightlosstracker.model.Gender
import com.marko.weightlosstracker.model.User
import javax.inject.Inject

class UserMapper @Inject constructor() : DomainMapper<UserDto, UserEntity, User> {

    override fun mapFromEntity(entity: UserEntity): User {
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

    override fun mapToEntity(domain: User): UserEntity {
        return UserEntity(
            username = domain.username,
            startWeight = domain.startWeight,
            currentWeight = domain.currentWeight,
            targetWeight = domain.targetWeight,
            startWaistSize = domain.startWaistSize,
            startBmi = domain.startBmi,
            height = domain.height,
            startDate = domain.startDate,
            age = domain.age,
            gender = domain.gender.name,
            goalName = domain.goalName
        )
    }

    override fun mapFromDto(dto: UserDto): User {
        return User(
            dto.id, dto.username, dto.startWeight, dto.currentWeight,
            dto.targetWeight, dto.startWaistSize, dto.startBmi, dto.height,
            dto.startDate, dto.age, Gender.valueOf(dto.gender), dto.goalName
        )
    }

    override fun mapToDto(domain: User): UserDto {
        return UserDto(
            domain.uuid, domain.username, domain.startWeight,
            domain.currentWeight, domain.targetWeight, domain.startWaistSize,
            domain.startBmi, domain.height, domain.startDate, domain.age,
            domain.gender.name, domain.goalName
        )
    }

    fun dtoToEntity(userDto: UserDto): UserEntity {
        return UserEntity(
            username = userDto.username,
            startWeight = userDto.startWeight,
            currentWeight = userDto.currentWeight,
            targetWeight = userDto.targetWeight,
            startWaistSize = userDto.startWaistSize,
            startBmi = userDto.startBmi,
            height = userDto.height,
            startDate = userDto.startDate,
            age = userDto.age,
            gender = userDto.gender,
            goalName = userDto.goalName
        )
    }
}