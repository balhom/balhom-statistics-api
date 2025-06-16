package org.balhom.statisticsapi.modules.transactionchanges.infrastructure.consumers

import io.quarkus.logging.Log
import io.smallrye.common.annotation.Blocking
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.modules.transactionchanges.application.TransactionChangesService
import org.balhom.statisticsapi.modules.transactionchanges.infrastructure.consumers.data.TransactionChangeEvent
import org.eclipse.microprofile.reactive.messaging.Incoming

@ApplicationScoped
class TransactionChangeEventConsumer(
    private val transactionChangesService: TransactionChangesService
) {

    @Incoming("transaction-events")
    @Blocking
    fun receive(event: TransactionChangeEvent) {
        Log.info("Consuming Kafka transaction change event: " + event.id)

        transactionChangesService
            .processChange(
                event.toChangeProps()
            )
    }
}