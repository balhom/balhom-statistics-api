package org.balhom.statisticsapi.modules.statistics.infrastructure.persistence

import com.mongodb.client.model.Filters
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.common.config.ReactiveMongoConfig
import org.balhom.statisticsapi.modules.statistics.domain.models.CategoryTransactionStatistic
import org.balhom.statisticsapi.modules.statistics.domain.repositories.CategoryTransactionStatisticRepository
import org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.CategoryTransactionStatisticMongoRepository
import org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.data.CategoryTransactionStatisticMongoEntity
import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import java.math.BigDecimal
import java.util.*

@ApplicationScoped
class CategoryTransactionStatisticRepositoryImpl(
    private val reactiveMongoConfig: ReactiveMongoConfig,
    private val categoryTransactionStatisticMongoRepository: CategoryTransactionStatisticMongoRepository
) : CategoryTransactionStatisticRepository {

    override fun findAllByCurrencyProfileIdAndTypeAndMonthAndYear(
        currencyProfileId: UUID,
        type: TransactionTypeEnum,
        month: Int,
        year: Int
    ): Uni<List<CategoryTransactionStatistic>> {
        return reactiveMongoConfig
            .getDatabase()
            .getCollection(
                CategoryTransactionStatisticMongoEntity.COLLECTION_NAME,
                CategoryTransactionStatisticMongoEntity::class.java
            )
            .find(
                Filters.and(
                    Filters.eq(
                        CategoryTransactionStatisticMongoEntity.CURRENCY_PROFILE_ID_FIELD_NAME,
                        currencyProfileId
                    ),
                    Filters.eq(
                        CategoryTransactionStatisticMongoEntity.TYPE_FIELD_NAME,
                        type
                    ),
                    Filters.eq(
                        CategoryTransactionStatisticMongoEntity.MONTH_FIELD_NAME,
                        month
                    ),
                    Filters.eq(
                        CategoryTransactionStatisticMongoEntity.YEAR_FIELD_NAME,
                        year
                    ),
                )
            )
            .map { it.toDomain() }
            .collect()
            .asList()
    }

    override fun findByCurrencyProfileIdAndTypeAndCategoryAndMonthAndYear(
        currencyProfileId: UUID,
        type: TransactionTypeEnum,
        category: String,
        month: Int,
        year: Int
    ): CategoryTransactionStatistic {
        return categoryTransactionStatisticMongoRepository
            .find(
                Filters.and(
                    Filters.eq(
                        CategoryTransactionStatisticMongoEntity.CURRENCY_PROFILE_ID_FIELD_NAME,
                        currencyProfileId
                    ),
                    Filters.eq(
                        CategoryTransactionStatisticMongoEntity.TYPE_FIELD_NAME,
                        type
                    ),
                    Filters.eq(
                        CategoryTransactionStatisticMongoEntity.CATEGORY_FIELD_NAME,
                        category
                    ),
                    Filters.eq(
                        CategoryTransactionStatisticMongoEntity.YEAR_FIELD_NAME,
                        year
                    ),
                    Filters.eq(
                        CategoryTransactionStatisticMongoEntity.MONTH_FIELD_NAME,
                        month
                    ),
                )
            )
            .firstResult()
            ?.toDomain()
            ?: CategoryTransactionStatistic(
                currencyProfileId = currencyProfileId,
                month = month,
                year = year,
                type = type,
                category = category,
                value = BigDecimal(0.0),
            )
    }

    override fun save(statistic: CategoryTransactionStatistic): CategoryTransactionStatistic {
        val entity = CategoryTransactionStatisticMongoEntity
            .fromDomain(statistic)

        categoryTransactionStatisticMongoRepository
            .persistOrUpdate(entity)

        return entity.toDomain()
    }

    override fun deleteAllByCurrencyProfileId(currencyProfileId: UUID) {
        categoryTransactionStatisticMongoRepository
            .delete(
                Filters.eq(
                    CategoryTransactionStatisticMongoEntity.CURRENCY_PROFILE_ID_FIELD_NAME,
                    currencyProfileId
                )
            )
    }
}