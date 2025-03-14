package org.balhom.statisticsapi.modules.currencyprofilechanges.application

import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.modules.currencyprofilechanges.domain.props.CurrencyProfileChangeProps

@ApplicationScoped
class CurrencyProfileChangesService {

    fun processChange(props: CurrencyProfileChangeProps) {
        // TODO call statistics service
    }
}
