package com.marko.weightlosstracker.data.local.mappers

import com.marko.weightlosstracker.data.local.model.WeightEntryCache
import com.marko.weightlosstracker.data.remote.model.RemoteWeightEntry
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.util.EntityMapper
import com.marko.weightlosstracker.util.RemoteEntityMapper
import javax.inject.Inject

class WeightEntryMapper @Inject constructor() : EntityMapper<WeightEntryCache, WeightEntry>,
    RemoteEntityMapper<RemoteWeightEntry, WeightEntry> {

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

    override fun mapFromRemoteEntity(entity: RemoteWeightEntry): WeightEntry {
        return WeightEntry(
            entity.uuid, entity.currentWeight, entity.waistSize, entity.date, entity.description
        )
    }

    override fun mapToRemoteEntity(domainModel: WeightEntry): RemoteWeightEntry {
        return RemoteWeightEntry(
            domainModel.uuid,
            domainModel.currentWeight,
            domainModel.waistSize,
            domainModel.date,
            domainModel.description
        )
    }

    fun remoteToLocal(remoteWeightEntry: RemoteWeightEntry): WeightEntryCache {
        return WeightEntryCache(
            remoteWeightEntry.uuid,
            remoteWeightEntry.currentWeight,
            remoteWeightEntry.waistSize,
            remoteWeightEntry.date,
            remoteWeightEntry.description
        )
    }
}