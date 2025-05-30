package org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.data

import io.quarkus.mongodb.panache.common.MongoEntity
import org.balhom.statisticsapi.modules.statistics.domain.models.DailyTransactionStatistic
import java.math.BigDecimal
import java.util.*

@MongoEntity(collection = DailyTransactionStatisticMongoEntity.COLLECTION_NAME)
data class DailyTransactionStatisticMongoEntity(
    var id: String,
    var currencyProfileId: UUID,
    var day: Int,
    var month: Int,
    var year: Int,
    var income: BigDecimal,
    var expenses: BigDecimal,
) {

    fun toDomain(): DailyTransactionStatistic {
        return DailyTransactionStatistic(
            currencyProfileId = currencyProfileId,
            day = day,
            month = month,
            year = year,
            income = income,
            expenses = expenses,
        )
    }

    companion object {
        const val COLLECTION_NAME = "dailyTransactionStatistics"

        const val CURRENCY_PROFILE_ID_FIELD_NAME = "currencyProfileId"
        const val DAY_FIELD_NAME = "day"
        const val MONTH_FIELD_NAME = "month"
        const val YEAR_FIELD_NAME = "year"

        fun fromDomain(domain: DailyTransactionStatistic): DailyTransactionStatisticMongoEntity =
            DailyTransactionStatisticMongoEntity(
                id = "${domain.currencyProfileId}-" +
                        "${String.format("%02d", domain.day)}-" +
                        "${String.format("%02d", domain.month)}-" +
                        domain.year,
                currencyProfileId = domain.currencyProfileId,
                day = domain.day,
                month = domain.month,
                year = domain.year,
                income = domain.income,
                expenses = domain.expenses,
            )
    }
}
