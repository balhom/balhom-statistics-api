package org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.data

import io.quarkus.mongodb.panache.common.MongoEntity
import org.balhom.statisticsapi.modules.statistics.domain.models.MonthlyTransactionStatistic
import java.math.BigDecimal
import java.util.*

@MongoEntity(collection = DailyTransactionStatisticMongoEntity.COLLECTION_NAME)
data class MonthlyTransactionStatisticMongoEntity(
    var id: String,
    var currencyProfileId: UUID,
    var month: Int,
    var year: Int,
    var income: BigDecimal,
    var expenses: BigDecimal,
) {

    fun toDomain(): MonthlyTransactionStatistic {
        return MonthlyTransactionStatistic(
            currencyProfileId = currencyProfileId,
            month = month,
            year = year,
            income = income,
            expenses = expenses,
        )
    }

    companion object {
        const val COLLECTION_NAME = "monthlyTransactionStatistics"

        const val CURRENCY_PROFILE_ID_FIELD_NAME = "currencyProfileId"
        const val MONTH_FIELD_NAME = "month"
        const val YEAR_FIELD_NAME = "year"

        fun fromDomain(domain: MonthlyTransactionStatistic): MonthlyTransactionStatisticMongoEntity =
            MonthlyTransactionStatisticMongoEntity(
                id = "${domain.currencyProfileId}-" +
                        "${String.format("%02d", domain.month)}-" +
                        domain.year,
                currencyProfileId = domain.currencyProfileId,
                month = domain.month,
                year = domain.year,
                income = domain.income,
                expenses = domain.expenses,
            )
    }
}
