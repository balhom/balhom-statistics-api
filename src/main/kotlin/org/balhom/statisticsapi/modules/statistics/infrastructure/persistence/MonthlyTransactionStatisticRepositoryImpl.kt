package org.balhom.statisticsapi.modules.statistics.infrastructure.persistence

import com.mongodb.client.model.Filters
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.common.config.ReactiveMongoConfig
import org.balhom.statisticsapi.modules.statistics.domain.models.MonthlyTransactionStatistic
import org.balhom.statisticsapi.modules.statistics.domain.repositories.MonthlyTransactionStatisticRepository
import org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.MonthlyTransactionStatisticMongoRepository
import org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.data.MonthlyTransactionStatisticMongoEntity
import java.math.BigDecimal
import java.util.*

@ApplicationScoped
class MonthlyTransactionStatisticRepositoryImpl(
    private val reactiveMongoConfig: ReactiveMongoConfig,
    private val monthlyTransactionStatisticsMongoRepository: MonthlyTransactionStatisticMongoRepository
) : MonthlyTransactionStatisticRepository {

    override fun findAllByCurrencyProfileIdAndYear(
        currencyProfileId: UUID,
        year: Int
    ): Uni<List<MonthlyTransactionStatistic>> {
        return reactiveMongoConfig
            .getDatabase()
            .getCollection(
                MonthlyTransactionStatisticMongoEntity.COLLECTION_NAME,
                MonthlyTransactionStatisticMongoEntity::class.java
            )
            .find(
                Filters.and(
                    Filters.eq(
                        MonthlyTransactionStatisticMongoEntity.CURRENCY_PROFILE_ID_FIELD_NAME,
                        currencyProfileId
                    ),
                    Filters.eq(
                        MonthlyTransactionStatisticMongoEntity.YEAR_FIELD_NAME,
                        year
                    ),
                )
            )
            .map { it.toDomain() }
            .collect()
            .asList()
    }

    override fun findByCurrencyProfileIdAndMonthAndYear(
        currencyProfileId: UUID,
        month: Int,
        year: Int
    ): MonthlyTransactionStatistic {
        return monthlyTransactionStatisticsMongoRepository
            .find(
                Filters.and(
                    Filters.eq(
                        MonthlyTransactionStatisticMongoEntity.CURRENCY_PROFILE_ID_FIELD_NAME,
                        currencyProfileId
                    ),
                    Filters.eq(
                        MonthlyTransactionStatisticMongoEntity.YEAR_FIELD_NAME,
                        year
                    ),
                    Filters.eq(
                        MonthlyTransactionStatisticMongoEntity.MONTH_FIELD_NAME,
                        month
                    ),
                )
            )
            .firstResult()
            ?.toDomain()
            ?: MonthlyTransactionStatistic(
                currencyProfileId = currencyProfileId,
                month = month,
                year = year,
                income = BigDecimal(0.0),
                expenses = BigDecimal(0.0)
            )
    }

    override fun save(statistic: MonthlyTransactionStatistic): MonthlyTransactionStatistic {
        val entity = MonthlyTransactionStatisticMongoEntity
            .fromDomain(statistic)

        monthlyTransactionStatisticsMongoRepository
            .persistOrUpdate(entity)

        return entity.toDomain()
    }

    override fun deleteAllByCurrencyProfileId(currencyProfileId: UUID) {
        monthlyTransactionStatisticsMongoRepository
            .delete(
                Filters.eq(
                    MonthlyTransactionStatisticMongoEntity.CURRENCY_PROFILE_ID_FIELD_NAME,
                    currencyProfileId
                )
            )
    }
}