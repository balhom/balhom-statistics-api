package org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.data

import io.quarkus.mongodb.panache.common.MongoEntity
import org.balhom.statisticsapi.modules.statistics.domain.models.CategoryTransactionStatistic
import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import java.math.BigDecimal
import java.util.*

@MongoEntity(collection = CategoryTransactionStatisticMongoEntity.COLLECTION_NAME)
data class CategoryTransactionStatisticMongoEntity(
    var id: String,
    var currencyProfileId: UUID,
    var month: Int,
    var year: Int,
    var type: TransactionTypeEnum,
    var category: String,
    var value: BigDecimal,
) {

    fun toDomain(): CategoryTransactionStatistic {
        return CategoryTransactionStatistic(
            currencyProfileId = currencyProfileId,
            month = month,
            year = year,
            type = type,
            category = category,
            value = value
        )
    }

    companion object {
        const val COLLECTION_NAME = "categoryTransactionStatistics"

        const val CURRENCY_PROFILE_ID_FIELD_NAME = "currencyProfileId"
        const val MONTH_FIELD_NAME = "month"
        const val YEAR_FIELD_NAME = "year"
        const val TYPE_FIELD_NAME = "type"
        const val CATEGORY_FIELD_NAME = "category"

        fun fromDomain(domain: CategoryTransactionStatistic): CategoryTransactionStatisticMongoEntity =
            CategoryTransactionStatisticMongoEntity(
                id = "${domain.currencyProfileId}-" +
                        "${String.format("%02d", domain.month)}-" +
                        "${domain.year}-${domain.type}-${domain.category}",
                currencyProfileId = domain.currencyProfileId,
                month = domain.month,
                year = domain.year,
                type = domain.type,
                category = domain.category,
                value = domain.value,
            )
    }
}
