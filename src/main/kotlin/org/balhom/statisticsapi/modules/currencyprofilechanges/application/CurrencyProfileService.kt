package org.balhom.statisticsapi.modules.currencyprofilechanges.application

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.clients.CurrencyProfileReferenceClient
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.exceptions.CurrencyProfileReferenceNotFoundException
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.models.CurrencyProfileReference
import java.util.*

@ApplicationScoped
class CurrencyProfileService(
    private val currencyProfileReferenceClient: CurrencyProfileReferenceClient,
) {
    fun getCurrencyProfileReferenceAndValidate(
        userId: UUID,
        currencyProfileId: UUID
    ): Uni<CurrencyProfileReference> {
        return currencyProfileReferenceClient
            .getById(currencyProfileId)
            .onItem()
            .transform { currencyProfileReference ->
                when {
                    currencyProfileReference == null ->
                        throw CurrencyProfileReferenceNotFoundException()

                    currencyProfileReference.userId != userId
                            && !currencyProfileReference.sharedUsers.any { it.id == userId } ->
                        throw CurrencyProfileReferenceNotFoundException()

                    else -> currencyProfileReference
                }
            }
    }
}
