package com.example.weightlosstracker.data.local.mappers

import com.example.weightlosstracker.data.local.model.WeightEntryCache
import com.example.weightlosstracker.domain.WeightEntry
import com.example.weightlosstracker.util.EntityMapper
import javax.inject.Inject

class WeightEntryMapper @Inject constructor() : EntityMapper<WeightEntryCache, WeightEntry> {

    override fun mapFromEntity(entity: WeightEntryCache): WeightEntry {
        return WeightEntry(
            entity.uuid, entity.currentWeight, entity.waistSize, entity.date, entity.description
        )
    }

    override fun mapToEntity(domainModel: WeightEntry): WeightEntryCache {
        return WeightEntryCache(
            domainModel.uuid,
            domainModel.currentWeight,
            domainModel.waistSize,
            domainModel.date,
            domainModel.description
        )
    }

    fun mapFromEntityList(entities: List<WeightEntryCache>): List<WeightEntry> {
        return entities.map { mapFromEntity(it) }
    }
}