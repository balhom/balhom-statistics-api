package org.balhom.statisticsapi.modules.currencyprofilechanges.infrastructure.consumers

import io.smallrye.common.annotation.Blocking
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.modules.currencyprofilechanges.application.CurrencyProfileChangesService
import org.balhom.statisticsapi.modules.currencyprofilechanges.infrastructure.consumers.data.CurrencyProfileChangeEvent
import org.eclipse.microprofile.reactive.messaging.Incoming

@ApplicationScoped
class CurrencyProfileChangeEventConsumer(
    private val currencyProfileChangesService: CurrencyProfileChangesService
) {

    @Incoming("currency-profile-events")
    @Blocking
    fun receive(event: CurrencyProfileChangeEvent) {
        currencyProfileChangesService
            .processChange(
                event.toChangeProps()
            )
    }
}