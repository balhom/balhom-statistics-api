package org.balhom.statisticsapi.modules.statistics.infrastructure.persistence

import com.mongodb.client.model.Filters
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.common.config.ReactiveMongoConfig
import org.balhom.statisticsapi.modules.statistics.domain.models.DailyTransactionStatistic
import org.balhom.statisticsapi.modules.statistics.domain.repositories.DailyTransactionStatisticRepository
import org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.DailyTransactionStatisticMongoRepository
import org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.data.DailyTransactionStatisticMongoEntity
import java.math.BigDecimal
import java.util.*

@ApplicationScoped
class DailyTransactionStatisticRepositoryImpl(
    private val reactiveMongoConfig: ReactiveMongoConfig,
    private val dailyTransactionStatisticsMongoRepository: DailyTransactionStatisticMongoRepository
) : DailyTransactionStatisticRepository {

    override fun findAllByCurrencyProfileIdAndMonthAndYear(
        currencyProfileId: UUID,
        month: Int,
        year: Int
    ): Uni<List<DailyTransactionStatistic>> {
        return reactiveMongoConfig
            .getDatabase()
            .getCollection(
                DailyTransactionStatisticMongoEntity.COLLECTION_NAME,
                DailyTransactionStatisticMongoEntity::class.java
            )
            .find(
                Filters.and(
                    Filters.eq(
                        DailyTransactionStatisticMongoEntity.CURRENCY_PROFILE_ID_FIELD_NAME,
                        currencyProfileId
                    ),
                    Filters.eq(
                        DailyTransactionStatisticMongoEntity.MONTH_FIELD_NAME,
                        month
                    ),
                    Filters.eq(
                        DailyTransactionStatisticMongoEntity.YEAR_FIELD_NAME,
                        year
                    ),
                )
            )
            .map { it.toDomain() }
            .collect()
            .asList()
    }

    override fun findByCurrencyProfileIdAndDayAndMonthAndYear(
        currencyProfileId: UUID,
        day: Int,
        month: Int,
        year: Int
    ): DailyTransactionStatistic {
        return dailyTransactionStatisticsMongoRepository
            .find(
                Filters.and(
                    Filters.eq(
                        DailyTransactionStatisticMongoEntity.CURRENCY_PROFILE_ID_FIELD_NAME,
                        currencyProfileId
                    ),
                    Filters.eq(
                        DailyTransactionStatisticMongoEntity.YEAR_FIELD_NAME,
                        year
                    ),
                    Filters.eq(
                        DailyTransactionStatisticMongoEntity.MONTH_FIELD_NAME,
                        month
                    ),
                    Filters.eq(
                        DailyTransactionStatisticMongoEntity.DAY_FIELD_NAME,
                        day
                    ),
                )
            )
            .firstResult()
            ?.toDomain()
            ?: DailyTransactionStatistic(
                currencyProfileId = currencyProfileId,
                day = day,
                month = month,
                year = year,
                income = BigDecimal(0.0),
                expenses = BigDecimal(0.0)
            )
    }

    override fun save(statistic: DailyTransactionStatistic): DailyTransactionStatistic {
        val entity = DailyTransactionStatisticMongoEntity
            .fromDomain(statistic)

        dailyTransactionStatisticsMongoRepository
            .persistOrUpdate(entity)

        return entity.toDomain()
    }

    override fun deleteAllByCurrencyProfileId(currencyProfileId: UUID) {
        dailyTransactionStatisticsMongoRepository
            .delete(
                Filters.eq(
                    DailyTransactionStatisticMongoEntity.CURRENCY_PROFILE_ID_FIELD_NAME,
                    currencyProfileId
                )
            )
    }
}