package org.balhom.statisticsapi.common.data.exceptions

open class ApiCodeException(
    val errorCode: Int,
    message: String
) : RuntimeException(message)
