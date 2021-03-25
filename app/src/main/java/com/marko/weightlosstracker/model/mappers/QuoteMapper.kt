package com.marko.weightlosstracker.model.mappers

import com.marko.weightlosstracker.data.local.entities.QuoteEntity
import com.marko.weightlosstracker.data.network.entities.QuoteDto
import com.marko.weightlosstracker.data.util.DomainMapper
import com.marko.weightlosstracker.model.Quote
import javax.inject.Inject

class QuoteMapper @Inject constructor() : DomainMapper<QuoteDto, QuoteEntity, Quote> {

    override fun mapFromEntity(entity: QuoteEntity): Quote {
        return Quote(
            entity.id, entity.author, entity.category, entity.quote
        )
    }

    override fun mapToEntity(domain: Quote): QuoteEntity {
        return QuoteEntity(
            domain.id, domain.author, domain.category, domain.quote
        )
    }

    override fun mapFromDto(dto: QuoteDto): Quote {
        return Quote(
            dto.id, dto.author, dto.category, dto.quote
        )
    }

    override fun mapToDto(domain: Quote): QuoteDto {
        return QuoteDto(
            domain.id, domain.author, domain.category, domain.quote
        )
    }
}