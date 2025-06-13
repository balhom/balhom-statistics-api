package org.balhom.statisticsapi.modules.currencyprofilechanges.domain.clients

import io.smallrye.mutiny.Uni
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.models.CurrencyProfileReference
import java.util.*

fun interface CurrencyProfileReferenceClient {
    fun getById(id: UUID): Uni<CurrencyProfileReference>
}
