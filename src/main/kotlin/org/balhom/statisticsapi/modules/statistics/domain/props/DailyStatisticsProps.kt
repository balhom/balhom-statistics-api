package org.balhom.statisticsapi.modules.statistics.domain.props

import org.balhom.statisticsapi.common.data.props.ObjectIdUserProps

data class DailyStatisticsProps(
    val currencyProfileIdAndUser: ObjectIdUserProps,
    val month: Int,
    val year: Int,
)
