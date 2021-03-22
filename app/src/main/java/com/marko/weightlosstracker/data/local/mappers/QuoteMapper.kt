package com.marko.weightlosstracker.data.local.mappers

import com.marko.weightlosstracker.data.local.model.QuoteCache
import com.marko.weightlosstracker.data.remote.model.QuoteResponse
import com.marko.weightlosstracker.model.Quote
import com.marko.weightlosstracker.data.util.EntityMapper
import com.marko.weightlosstracker.data.util.RemoteEntityMapper
import javax.inject.Inject

class QuoteMapper @Inject constructor() : EntityMapper<QuoteCache, Quote>,
    RemoteEntityMapper<QuoteResponse, Quote> {

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

    override fun mapFromRemoteEntity(entity: QuoteResponse): Quote {
        return Quote(
            entity.id, entity.author, entity.category, entity.quote
        )
    }

    override fun mapToRemoteEntity(domainModel: Quote): QuoteResponse {
        return QuoteResponse(
            domainModel.id, domainModel.author, domainModel.category, domainModel.quote
        )
    }
}