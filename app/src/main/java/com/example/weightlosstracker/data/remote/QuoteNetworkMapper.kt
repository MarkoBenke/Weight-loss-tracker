package com.example.weightlosstracker.data.remote

import com.example.weightlosstracker.data.remote.response.QuoteResponse
import com.example.weightlosstracker.domain.Quote
import com.example.weightlosstracker.util.EntityMapper
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