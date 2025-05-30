package org.balhom.statisticsapi.modules.statistics.infrastructure.persistence

import com.mongodb.client.model.Filters
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.common.config.ReactiveMongoConfig
import org.balhom.statisticsapi.modules.statistics.domain.models.MonthlySavingsStatistic
import org.balhom.statisticsapi.modules.statistics.domain.repositories.MonthlySavingsStatisticRepository
import org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.MonthlySavingsStatisticMongoRepository
import org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.data.MonthlySavingsStatisticMongoEntity
import java.math.BigDecimal
import java.util.*

@ApplicationScoped
class MonthlySavingsStatisticRepositoryImpl(
    private val reactiveMongoConfig: ReactiveMongoConfig,
    private val monthlySavingsStatisticMongoRepository: MonthlySavingsStatisticMongoRepository
) : MonthlySavingsStatisticRepository {

    override fun findAllByCurrencyProfileIdAndYear(
        currencyProfileId: UUID,
        year: Int
    ): Uni<List<MonthlySavingsStatistic>> {
        return reactiveMongoConfig
            .getDatabase()
            .getCollection(
                MonthlySavingsStatisticMongoEntity.COLLECTION_NAME,
                MonthlySavingsStatisticMongoEntity::class.java
            )
            .find(
                Filters.and(
                    Filters.eq(
                        MonthlySavingsStatisticMongoEntity.CURRENCY_PROFILE_ID_FIELD_NAME,
                        currencyProfileId
                    ),
                    Filters.eq(
                        MonthlySavingsStatisticMongoEntity.YEAR_FIELD_NAME,
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
    ): MonthlySavingsStatistic {
        return monthlySavingsStatisticMongoRepository
            .find(
                Filters.and(
                    Filters.eq(
                        MonthlySavingsStatisticMongoEntity.CURRENCY_PROFILE_ID_FIELD_NAME,
                        currencyProfileId
                    ),
                    Filters.eq(
                        MonthlySavingsStatisticMongoEntity.YEAR_FIELD_NAME,
                        year
                    ),
                    Filters.eq(
                        MonthlySavingsStatisticMongoEntity.MONTH_FIELD_NAME,
                        month
                    ),
                )
            )
            .firstResult()
            ?.toDomain()
            ?: MonthlySavingsStatistic(
                currencyProfileId = currencyProfileId,
                month = month,
                year = year,
                savings = BigDecimal(0.0),
                goal = BigDecimal(0.0)
            )
    }

    override fun save(statistic: MonthlySavingsStatistic): MonthlySavingsStatistic {
        val entity = MonthlySavingsStatisticMongoEntity
            .fromDomain(statistic)

        monthlySavingsStatisticMongoRepository
            .persistOrUpdate(entity)

        return entity.toDomain()
    }

    override fun deleteAllByCurrencyProfileId(currencyProfileId: UUID) {
        monthlySavingsStatisticMongoRepository
            .delete(
                Filters.eq(
                    MonthlySavingsStatisticMongoEntity.CURRENCY_PROFILE_ID_FIELD_NAME,
                    currencyProfileId
                )
            )
    }
}