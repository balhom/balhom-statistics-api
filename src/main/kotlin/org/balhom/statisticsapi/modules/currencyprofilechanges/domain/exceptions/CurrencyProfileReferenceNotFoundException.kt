package org.balhom.statisticsapi.modules.currencyprofilechanges.domain.exceptions

import org.balhom.statisticsapi.common.data.exceptions.ApiCodeException

class CurrencyProfileReferenceNotFoundException : ApiCodeException(
    errorCode = 200,
    message = "Currency profile reference not found"
)
