package org.balhom.statisticsapi.modules.statistics.infrastructure.persistence

import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.common.config.ReactiveMongoConfig
import org.balhom.statisticsapi.modules.statistics.domain.models.YearlySavingsStatistic
import org.balhom.statisticsapi.modules.statistics.domain.repositories.YearlySavingsStatisticRepository
import org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.YearlySavingsStatisticMongoRepository
import org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.data.MonthlySavingsStatisticMongoEntity
import org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.data.YearlySavingsStatisticMongoEntity
import java.math.BigDecimal
import java.util.*

@ApplicationScoped
class YearlySavingsStatisticRepositoryImpl(
    private val reactiveMongoConfig: ReactiveMongoConfig,
    private val yearlySavingsStatisticMongoRepository: YearlySavingsStatisticMongoRepository
) : YearlySavingsStatisticRepository {

    override fun findAllByCurrencyProfileId(
        currencyProfileId: UUID
    ): Uni<List<YearlySavingsStatistic>> {
        return reactiveMongoConfig
            .getDatabase()
            .getCollection(
                YearlySavingsStatisticMongoEntity.COLLECTION_NAME,
                YearlySavingsStatisticMongoEntity::class.java
            )
            .aggregate(
                listOf(
                    Aggregates.match(
                        Filters.eq(
                            YearlySavingsStatisticMongoEntity.CURRENCY_PROFILE_ID_FIELD_NAME,
                            currencyProfileId
                        )
                    ),
                    Aggregates.sort(
                        Sorts.descending(
                            YearlySavingsStatisticMongoEntity.YEAR_FIELD_NAME
                        )
                    ),
                    Aggregates.limit(10)
                )
            )
            .map { it.toDomain() }
            .collect()
            .asList()
    }

    override fun findByCurrencyProfileIdAndYear(
        currencyProfileId: UUID,
        year: Int
    ): YearlySavingsStatistic {
        return yearlySavingsStatisticMongoRepository
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
            .firstResult()
            ?.toDomain()
            ?: YearlySavingsStatistic(
                currencyProfileId = currencyProfileId,
                year = year,
                savings = BigDecimal(0.0),
                goal = BigDecimal(0.0)
            )
    }

    override fun save(statistic: YearlySavingsStatistic): YearlySavingsStatistic {
        val entity = YearlySavingsStatisticMongoEntity
            .fromDomain(statistic)

        yearlySavingsStatisticMongoRepository
            .persistOrUpdate(entity)

        return entity.toDomain()
    }

    override fun deleteAllByCurrencyProfileId(currencyProfileId: UUID) {
        yearlySavingsStatisticMongoRepository
            .delete(
                Filters.eq(
                    YearlySavingsStatisticMongoEntity.CURRENCY_PROFILE_ID_FIELD_NAME,
                    currencyProfileId
                )
            )
    }
}