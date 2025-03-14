package org.balhom.statisticsapi.modules.currencyprofilechanges.infrastructure.consumers

import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.modules.currencyprofilechanges.application.CurrencyProfileChangesService
import org.balhom.statisticsapi.modules.currencyprofilechanges.infrastructure.consumers.data.CurrencyProfileChangeEvent
import org.eclipse.microprofile.reactive.messaging.Incoming

@ApplicationScoped
class CurrencyProfileChangeEventConsumer(
    private val currencyProfileChangesService: CurrencyProfileChangesService
) {

    @Incoming("currency-profile-events")
    fun receive(event: CurrencyProfileChangeEvent) {
        currencyProfileChangesService
            .processChange(
                event.toChangeProps()
            )
    }
}