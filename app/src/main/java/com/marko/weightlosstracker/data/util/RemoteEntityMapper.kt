package com.marko.weightlosstracker.data.util

interface RemoteEntityMapper<Entity, DomainModel> {

    fun mapFromRemoteEntity(entity: Entity): DomainModel
    fun mapToRemoteEntity(domainModel: DomainModel): Entity
}