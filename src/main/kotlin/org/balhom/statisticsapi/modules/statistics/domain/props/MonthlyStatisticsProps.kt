package org.balhom.statisticsapi.modules.statistics.domain.props

import java.util.UUID

data class MonthlyStatisticsProps(
    val userId: UUID,
    val year: Int,
)
