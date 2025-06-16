package org.balhom.statisticsapi.modules.statistics.domain.props

import org.balhom.statisticsapi.common.data.props.ObjectIdUserProps
import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum

data class CategoryStatisticsProps(
    val currencyProfileIdAndUser: ObjectIdUserProps,
    val month: Int,
    val year: Int,
    val type: TransactionTypeEnum,
)
