package org.balhom.statisticsapi.modules.statistics.domain.props

import java.util.UUID

data class DailyStatisticsProps(
    val userId: UUID,
    val month: Int,
    val year: Int,
)
