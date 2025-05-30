package org.balhom.statisticsapi.modules.currencyprofilechanges.domain.clients

import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.models.CurrencyProfileReference
import java.util.*

fun interface CurrencyProfileReferenceClient {
    fun getById(id: UUID): CurrencyProfileReference?
}
