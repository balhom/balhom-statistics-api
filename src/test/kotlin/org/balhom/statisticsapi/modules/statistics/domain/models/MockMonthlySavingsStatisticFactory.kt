package org.balhom.statisticsapi.modules.statistics.domain.models

import org.balhom.statisticsapi.common.utils.TestDataUtils.Companion.randomBigDecimal
import org.balhom.statisticsapi.common.utils.TestDataUtils.Companion.randomInt
import java.util.*

class MockMonthlySavingsStatisticFactory {
    companion object {
        fun create(): MonthlySavingsStatistic {
            val currencyProfileId = UUID.randomUUID()

            return MonthlySavingsStatistic(
                currencyProfileId = currencyProfileId,
                month = randomInt(1, 12),
                year = randomInt(0, 5000),
                savings = randomBigDecimal(),
                goal = randomBigDecimal(0.0, 10000.0),
            )
        }
    }
}