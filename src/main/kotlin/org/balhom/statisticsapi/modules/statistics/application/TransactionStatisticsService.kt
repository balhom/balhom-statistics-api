package org.balhom.statisticsapi.modules.statistics.application

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.modules.statistics.domain.models.DailyTransactionStatistic
import org.balhom.statisticsapi.modules.statistics.domain.models.MonthlyTransactionStatistic
import org.balhom.statisticsapi.modules.statistics.domain.props.DailyStatisticsProps
import org.balhom.statisticsapi.modules.statistics.domain.props.MonthlyStatisticsProps

@ApplicationScoped
class TransactionStatisticsService {

    fun getMonthlyStatistics(props: MonthlyStatisticsProps):
            Uni<List<MonthlyTransactionStatistic>> =
        transactionStatisticsRepository
            .findAllByUserIdAndYear(
                props.userId,
                props.year
            )

    fun getDailyStatistics(props: DailyStatisticsProps):
            Uni<List<DailyTransactionStatistic>> =
        transactionStatisticsRepository
            .findAllByUserIdAndMonthAndYear(
                props.userId,
                props.month,
                props.year
            )

}
