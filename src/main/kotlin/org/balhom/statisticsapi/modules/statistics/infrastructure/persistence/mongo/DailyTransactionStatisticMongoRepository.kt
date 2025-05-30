package org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo

import io.quarkus.mongodb.panache.kotlin.PanacheMongoRepository
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.modules.statistics.infrastructure.persistence.mongo.data.DailyTransactionStatisticMongoEntity

@ApplicationScoped
class DailyTransactionStatisticMongoRepository : PanacheMongoRepository<DailyTransactionStatisticMongoEntity>
