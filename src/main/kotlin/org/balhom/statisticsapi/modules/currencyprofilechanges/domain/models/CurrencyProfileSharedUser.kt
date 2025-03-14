package org.balhom.statisticsapi.modules.currencyprofilechanges.domain.models

import java.util.UUID

data class CurrencyProfileSharedUser(
    var id: UUID,
    var email: String,
)
