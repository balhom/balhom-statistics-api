package org.balhom.statisticsapi.modules.currencyprofilechanges.infrastructure.clients.responses

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers
import java.math.BigDecimal
import java.util.*

data class CurrencyProfileResponse(
    val id: UUID,
    @JsonDeserialize(using = NumberDeserializers.BigDecimalDeserializer::class)
    val balance: BigDecimal,
    @JsonDeserialize(using = NumberDeserializers.BigDecimalDeserializer::class)
    val goalMonthlySaving: BigDecimal,
    @JsonDeserialize(using = NumberDeserializers.BigDecimalDeserializer::class)
    val goalYearlySaving: BigDecimal,
    val ownerId: UUID
)
