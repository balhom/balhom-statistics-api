package org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.data

import io.quarkus.mongodb.panache.common.MongoEntity
import org.balhom.statisticsapi.modules.statistics.domain.models.MonthlySavingsStatistic
import java.math.BigDecimal
import java.util.*

@MongoEntity(collection = DailyTransactionStatisticMongoEntity.COLLECTION_NAME)
data class MonthlySavingsStatisticMongoEntity(
    var id: String,
    var currencyProfileId: UUID,
    var month: Int,
    var year: Int,
    var savings: BigDecimal,
    var goal: BigDecimal,
) {

    fun toDomain(): MonthlySavingsStatistic {
        return MonthlySavingsStatistic(
            currencyProfileId = currencyProfileId,
            month = month,
            year = year,
            savings = savings,
            goal = goal,
        )
    }

    companion object {
        const val COLLECTION_NAME = "monthlySavingsStatistic"

        const val CURRENCY_PROFILE_ID_FIELD_NAME = "currencyProfileId"
        const val MONTH_FIELD_NAME = "month"
        const val YEAR_FIELD_NAME = "year"

        fun fromDomain(domain: MonthlySavingsStatistic): MonthlySavingsStatisticMongoEntity =
            MonthlySavingsStatisticMongoEntity(
                id = "${domain.currencyProfileId}-" +
                        "${String.format("%02d", domain.month)}-" +
                        domain.year,
                currencyProfileId = domain.currencyProfileId,
                month = domain.month,
                year = domain.year,
                savings = domain.savings,
                goal = domain.goal,
            )
    }
}
