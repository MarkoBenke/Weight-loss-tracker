package com.marko.weightlosstracker.model.mappers

import com.marko.weightlosstracker.data.local.entities.WeightEntryEntity
import com.marko.weightlosstracker.data.network.entities.WeightEntryDto
import com.marko.weightlosstracker.data.util.DomainMapper
import com.marko.weightlosstracker.model.WeightEntry
import javax.inject.Inject

class WeightEntryMapper @Inject constructor() : DomainMapper<WeightEntryDto,
        WeightEntryEntity, WeightEntry> {

    override fun mapFromEntity(entity: WeightEntryEntity): WeightEntry {
        return WeightEntry(
            entity.uuid, entity.currentWeight, entity.waistSize, entity.date, entity.description
        )
    }

    override fun mapToEntity(domain: WeightEntry): WeightEntryEntity {
        return WeightEntryEntity(
            domain.uuid,
            domain.currentWeight,
            domain.waistSize,
            domain.date,
            domain.description
        )
    }

    fun mapFromEntityList(entities: List<WeightEntryEntity>): List<WeightEntry> {
        return entities.map { mapFromEntity(it) }
    }

    fun dtoToEntity(weightEntryDto: WeightEntryDto): WeightEntryEntity {
        return WeightEntryEntity(
            weightEntryDto.uuid,
            weightEntryDto.currentWeight,
            weightEntryDto.waistSize,
            weightEntryDto.date,
            weightEntryDto.description
        )
    }

    override fun mapFromDto(dto: WeightEntryDto): WeightEntry {
        return WeightEntry(
            dto.uuid, dto.currentWeight, dto.waistSize, dto.date, dto.description
        )
    }

    override fun mapToDto(domain: WeightEntry): WeightEntryDto {
        return WeightEntryDto(
            domain.uuid,
            domain.currentWeight,
            domain.waistSize,
            domain.date,
            domain.description
        )
    }
}