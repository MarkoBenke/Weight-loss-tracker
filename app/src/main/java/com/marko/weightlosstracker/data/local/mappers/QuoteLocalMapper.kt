package com.marko.weightlosstracker.data.local.mappers

import com.marko.weightlosstracker.data.local.model.QuoteCache
import com.marko.weightlosstracker.domain.Quote
import com.marko.weightlosstracker.util.EntityMapper
import javax.inject.Inject

class QuoteLocalMapper @Inject constructor(): EntityMapper<QuoteCache, Quote> {

    override fun mapFromEntity(entity: QuoteCache): Quote {
        return Quote(
            entity.id, entity.author, entity.category, entity.quote
        )
    }

    override fun mapToEntity(domainModel: Quote): QuoteCache {
        return QuoteCache(
            domainModel.id, domainModel.author, domainModel.category, domainModel.quote
        )
    }

    fun mapFromEntityList(entities: List<QuoteCache>): List<Quote> {
        return entities.map { mapFromEntity(it) }
    }
}