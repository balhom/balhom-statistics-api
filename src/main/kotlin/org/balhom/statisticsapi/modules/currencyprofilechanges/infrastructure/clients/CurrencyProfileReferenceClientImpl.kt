package org.balhom.statisticsapi.modules.currencyprofilechanges.infrastructure.clients

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.WebApplicationException
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.clients.CurrencyProfileReferenceClient
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.models.CurrencyProfileReference
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.util.*

@ApplicationScoped
class CurrencyProfileReferenceClientImpl : CurrencyProfileReferenceClient {

    @RestClient
    lateinit var currencyProfileApiClient: CurrencyProfileApiClient

    override fun getById(id: UUID): CurrencyProfileReference? {
        return try {
            val currencyProfileResponse = currencyProfileApiClient
                .getById(id)
            val currencyProfileSharedUsers = currencyProfileApiClient
                .getSharedUsersById(id)

            CurrencyProfileReference(
                id = currencyProfileResponse.id,
                balance = currencyProfileResponse.balance,
                goalMonthlySaving = currencyProfileResponse.goalMonthlySaving,
                goalYearlySaving = currencyProfileResponse.goalYearlySaving,
                sharedUsers = currencyProfileSharedUsers,
                userId = currencyProfileResponse.ownerId
            )
        } catch (e: WebApplicationException) {
            null
        }
    }
}