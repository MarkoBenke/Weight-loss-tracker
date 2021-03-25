package com.marko.weightlosstracker.data.util

interface DomainMapper<Dto, Entity, Domain> {

    fun mapFromDto(dto: Dto): Domain
    fun mapToDto(domain: Domain): Dto

    fun mapFromEntity(entity: Entity): Domain
    fun mapToEntity(domain: Domain): Entity
}