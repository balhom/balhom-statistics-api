package org.balhom.statisticsapi.modules.statistics.domain.props

import org.balhom.statisticsapi.common.data.props.ObjectIdUserProps

data class MonthlyStatisticsProps(
    val currencyProfileIdAndUser: ObjectIdUserProps,
    val year: Int,
)
