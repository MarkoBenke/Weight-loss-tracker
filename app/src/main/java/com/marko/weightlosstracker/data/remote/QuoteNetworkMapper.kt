package com.marko.weightlosstracker.data.remote

import com.marko.weightlosstracker.data.remote.response.QuoteResponse
import com.marko.weightlosstracker.domain.Quote
import com.marko.weightlosstracker.util.EntityMapper
import javax.inject.Inject

class QuoteNetworkMapper @Inject constructor() : EntityMapper<QuoteResponse, Quote> {

    override fun mapFromEntity(entity: QuoteResponse): Quote {
        return Quote(
            entity.id, entity.author, entity.category, entity.quote
        )
    }

    override fun mapToEntity(domainModel: Quote): QuoteResponse {
        return QuoteResponse(
            domainModel.id, domainModel.author, domainModel.category, domainModel.quote
        )
    }
}