package org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.data

import io.quarkus.mongodb.panache.common.MongoEntity
import org.balhom.statisticsapi.modules.statistics.domain.models.YearlySavingsStatistic
import java.math.BigDecimal
import java.util.*

@MongoEntity(collection = YearlySavingsStatisticMongoEntity.COLLECTION_NAME)
data class YearlySavingsStatisticMongoEntity(
    var id: String,
    var currencyProfileId: UUID,
    var year: Int,
    var savings: BigDecimal,
    var goal: BigDecimal,
) {

    fun toDomain(): YearlySavingsStatistic {
        return YearlySavingsStatistic(
            currencyProfileId = currencyProfileId,
            year = year,
            savings = savings,
            goal = goal,
        )
    }

    companion object {
        const val COLLECTION_NAME = "yearlySavingsStatistic"

        const val CURRENCY_PROFILE_ID_FIELD_NAME = "currencyProfileId"
        const val YEAR_FIELD_NAME = "year"

        fun fromDomain(domain: YearlySavingsStatistic): YearlySavingsStatisticMongoEntity =
            YearlySavingsStatisticMongoEntity(
                id = "${domain.currencyProfileId}-" +
                        domain.year,
                currencyProfileId = domain.currencyProfileId,
                year = domain.year,
                savings = domain.savings,
                goal = domain.goal,
            )
    }
}
