package org.balhom.statisticsapi.modules.currencyprofilechanges.application

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.clients.CurrencyProfileReferenceClient
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.exceptions.CurrencyProfileReferenceNotFoundException
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.models.CurrencyProfileReference
import java.util.*

@ApplicationScoped
@Transactional
class CurrencyProfileService(
    private val currencyProfileReferenceClient: CurrencyProfileReferenceClient,
) {
    fun getCurrencyProfileReferenceAndValidate(
        userId: UUID,
        currencyProfileId: UUID
    ): CurrencyProfileReference {
        val currencyProfileReference = currencyProfileReferenceClient
            .getById(currencyProfileId)
            ?: throw CurrencyProfileReferenceNotFoundException()

        // Check user permission
        if (
            currencyProfileReference.userId != userId
            && !currencyProfileReference.sharedUsers.any({ it.id == userId })
        ) {
            throw CurrencyProfileReferenceNotFoundException()
        }

        return currencyProfileReference
    }
}
