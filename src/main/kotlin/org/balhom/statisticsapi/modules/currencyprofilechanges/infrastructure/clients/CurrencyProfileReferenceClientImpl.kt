package org.balhom.statisticsapi.modules.currencyprofilechanges.infrastructure.clients

import io.smallrye.mutiny.Uni
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

    override fun getById(id: UUID): Uni<CurrencyProfileReference> {
        return currencyProfileApiClient
            .getById(id)
            .chain { currencyProfileResponse ->
                currencyProfileApiClient
                    .getSharedUsersById(id)
                    .map { currencyProfileSharedUsers ->
                        CurrencyProfileReference(
                            id = currencyProfileResponse.id,
                            balance = currencyProfileResponse.balance,
                            goalMonthlySaving = currencyProfileResponse.goalMonthlySaving,
                            goalYearlySaving = currencyProfileResponse.goalYearlySaving,
                            sharedUsers = currencyProfileSharedUsers,
                            userId = currencyProfileResponse.ownerId
                        )
                    }
            }
            .onFailure(WebApplicationException::class.java)
            .recoverWithUni(Uni.createFrom().nullItem())
    }
}