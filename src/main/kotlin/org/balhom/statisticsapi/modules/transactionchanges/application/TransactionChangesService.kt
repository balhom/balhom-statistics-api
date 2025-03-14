package org.balhom.statisticsapi.modules.transactionchanges.application

import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.modules.transactionchanges.domain.props.TransactionChangeProps

@ApplicationScoped
class TransactionChangesService {

    fun processChange(props: TransactionChangeProps) {
        // TODO call statistics service
    }
}
