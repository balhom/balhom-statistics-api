package org.balhom.statisticsapi.modules.statistics.application

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.balhom.statisticsapi.modules.currencyprofilechanges.application.CurrencyProfileService
import org.balhom.statisticsapi.modules.statistics.domain.models.CategoryTransactionStatistic
import org.balhom.statisticsapi.modules.statistics.domain.models.DailyTransactionStatistic
import org.balhom.statisticsapi.modules.statistics.domain.models.MonthlyTransactionStatistic
import org.balhom.statisticsapi.modules.statistics.domain.props.CategoryStatisticsProps
import org.balhom.statisticsapi.modules.statistics.domain.props.DailyStatisticsProps
import org.balhom.statisticsapi.modules.statistics.domain.props.MonthlyStatisticsProps
import org.balhom.statisticsapi.modules.statistics.domain.props.TransactionStatisticToAddProps
import org.balhom.statisticsapi.modules.statistics.domain.repositories.CategoryTransactionStatisticRepository
import org.balhom.statisticsapi.modules.statistics.domain.repositories.DailyTransactionStatisticRepository
import org.balhom.statisticsapi.modules.statistics.domain.repositories.MonthlyTransactionStatisticRepository
import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import java.math.BigDecimal
import java.util.UUID

@ApplicationScoped
class TransactionStatisticsService(
    private val currencyProfileService: CurrencyProfileService,
    private val monthlyTransactionStatisticRepository: MonthlyTransactionStatisticRepository,
    private val dailyTransactionStatisticRepository: DailyTransactionStatisticRepository,
    private val categoryTransactionStatisticRepository: CategoryTransactionStatisticRepository
) {

    fun getMonthlyStatistics(props: MonthlyStatisticsProps):
            Uni<List<MonthlyTransactionStatistic>> {
        val userId = props.currencyProfileIdAndUser.userId
        val currencyProfileId = props.currencyProfileIdAndUser.id

        return currencyProfileService.getCurrencyProfileReferenceAndValidate(
            userId,
            currencyProfileId
        )
            .chain { _ ->
                monthlyTransactionStatisticRepository
                    .findAllByCurrencyProfileIdAndYear(
                        currencyProfileId,
                        props.year
                    )
            }
    }

    fun getDailyStatistics(props: DailyStatisticsProps):
            Uni<List<DailyTransactionStatistic>> {
        val userId = props.currencyProfileIdAndUser.userId
        val currencyProfileId = props.currencyProfileIdAndUser.id

        return currencyProfileService.getCurrencyProfileReferenceAndValidate(
            userId,
            currencyProfileId
        )
            .chain { _ ->
                dailyTransactionStatisticRepository
                    .findAllByCurrencyProfileIdAndMonthAndYear(
                        currencyProfileId,
                        props.month,
                        props.year
                    )
            }
    }

    fun getCategoryStatistics(props: CategoryStatisticsProps):
            Uni<List<CategoryTransactionStatistic>> {
        val userId = props.currencyProfileIdAndUser.userId
        val currencyProfileId = props.currencyProfileIdAndUser.id

        return currencyProfileService.getCurrencyProfileReferenceAndValidate(
            userId,
            currencyProfileId
        )
            .chain { _ ->
                categoryTransactionStatisticRepository
                    .findAllByCurrencyProfileIdAndTypeAndMonthAndYear(
                        currencyProfileId,
                        props.type,
                        props.month,
                        props.year
                    )
            }
    }

    fun add(props: TransactionStatisticToAddProps) {
        // Add expenses and income for daily statistic
        addToDailyTransactionStatistic(props)

        // Add expenses and income for monthly statistic
        addToMonthlyTransactionStatistic(props)

        // Add income for category statistic
        addToIncomeCategoryTransactionStatistic(props)

        // Add expenses for category statistic
        addToExpenseCategoryTransactionStatistic(props)
    }

    private fun addToDailyTransactionStatistic(
        props: TransactionStatisticToAddProps
    ) {
        val dailyStatistic = dailyTransactionStatisticRepository
            .findByCurrencyProfileIdAndDayAndMonthAndYear(
                currencyProfileId = props.currencyProfileId,
                day = props.date.dayOfMonth,
                month = props.date.monthValue,
                year = props.date.year
            )

        dailyStatistic.income += props.incomeToAdd
        dailyStatistic.expenses += props.expensesToAdd

        dailyTransactionStatisticRepository.save(
            dailyStatistic
        )

        if (props.oldDate != null) {
            val oldDailyStatistic = if (props.date == props.oldDate) {
                dailyStatistic
            } else {
                dailyTransactionStatisticRepository
                    .findByCurrencyProfileIdAndDayAndMonthAndYear(
                        currencyProfileId = props.currencyProfileId,
                        day = props.oldDate.dayOfMonth,
                        month = props.oldDate.monthValue,
                        year = props.oldDate.year
                    )
            }

            if (props.oldIncomeAdded != null) {
                oldDailyStatistic.income -= props.oldIncomeAdded
            }
            if (props.oldExpensesAdded != null) {
                oldDailyStatistic.expenses -= props.oldExpensesAdded
            }

            dailyTransactionStatisticRepository.save(
                oldDailyStatistic
            )
        }
    }

    private fun addToMonthlyTransactionStatistic(
        props: TransactionStatisticToAddProps
    ) {
        val monthlyStatistic = monthlyTransactionStatisticRepository
            .findByCurrencyProfileIdAndMonthAndYear(
                currencyProfileId = props.currencyProfileId,
                month = props.date.monthValue,
                year = props.date.year
            )

        monthlyStatistic.income += props.incomeToAdd
        monthlyStatistic.expenses += props.expensesToAdd

        monthlyTransactionStatisticRepository.save(monthlyStatistic)

        if (props.oldDate != null) {
            val oldMonthlyStatistic = if (props.date == props.oldDate) {
                monthlyStatistic
            } else {
                monthlyTransactionStatisticRepository
                    .findByCurrencyProfileIdAndMonthAndYear(
                        currencyProfileId = props.currencyProfileId,
                        month = props.oldDate.monthValue,
                        year = props.oldDate.year
                    )
            }

            if (props.oldIncomeAdded != null) {
                oldMonthlyStatistic.income -= props.oldIncomeAdded
            }
            if (props.oldExpensesAdded != null) {
                oldMonthlyStatistic.expenses -= props.oldExpensesAdded
            }

            monthlyTransactionStatisticRepository.save(oldMonthlyStatistic)
        }
    }

    private fun addToIncomeCategoryTransactionStatistic(
        props: TransactionStatisticToAddProps
    ) {
        if (props.incomeToAdd != BigDecimal(0.0)) {
            val incomeCategoryStatistic = categoryTransactionStatisticRepository
                .findByCurrencyProfileIdAndTypeAndCategoryAndMonthAndYear(
                    currencyProfileId = props.currencyProfileId,
                    type = TransactionTypeEnum.INCOME,
                    category = props.category,
                    month = props.date.monthValue,
                    year = props.date.year,
                )
            incomeCategoryStatistic.value += props.incomeToAdd

            categoryTransactionStatisticRepository.save(incomeCategoryStatistic)

            if (props.oldDate != null && props.oldCategory != null) {
                val oldIncomeCategoryStatistic =
                    if (props.date == props.oldDate && props.category == props.oldCategory) {
                        incomeCategoryStatistic
                    } else {
                        categoryTransactionStatisticRepository
                            .findByCurrencyProfileIdAndTypeAndCategoryAndMonthAndYear(
                                currencyProfileId = props.currencyProfileId,
                                type = TransactionTypeEnum.INCOME,
                                category = props.oldCategory,
                                month = props.oldDate.monthValue,
                                year = props.oldDate.year,
                            )
                    }

                if (props.oldIncomeAdded != null) {
                    oldIncomeCategoryStatistic.value -= props.oldIncomeAdded
                }

                categoryTransactionStatisticRepository.save(oldIncomeCategoryStatistic)
            }
        }
    }

    private fun addToExpenseCategoryTransactionStatistic(
        props: TransactionStatisticToAddProps
    ) {
        if (props.expensesToAdd != BigDecimal(0.0)) {
            val expenseCategoryStatistic = categoryTransactionStatisticRepository
                .findByCurrencyProfileIdAndTypeAndCategoryAndMonthAndYear(
                    currencyProfileId = props.currencyProfileId,
                    type = TransactionTypeEnum.EXPENSE,
                    category = props.category,
                    month = props.date.monthValue,
                    year = props.date.year,
                )
            expenseCategoryStatistic.value += props.expensesToAdd

            categoryTransactionStatisticRepository.save(expenseCategoryStatistic)

            if (props.oldDate != null && props.oldCategory != null) {
                val oldExpenseCategoryStatistic =
                    if (props.date == props.oldDate && props.category == props.oldCategory) {
                        expenseCategoryStatistic
                    } else {
                        categoryTransactionStatisticRepository
                            .findByCurrencyProfileIdAndTypeAndCategoryAndMonthAndYear(
                                currencyProfileId = props.currencyProfileId,
                                type = TransactionTypeEnum.EXPENSE,
                                category = props.oldCategory,
                                month = props.oldDate.monthValue,
                                year = props.oldDate.year,
                            )
                    }

                if (props.oldExpensesAdded != null) {
                    oldExpenseCategoryStatistic.value -= props.oldExpensesAdded
                }

                categoryTransactionStatisticRepository.save(oldExpenseCategoryStatistic)
            }
        }
    }

    fun deleteAll(currencyProfileId: UUID) {
        dailyTransactionStatisticRepository
            .deleteAllByCurrencyProfileId(currencyProfileId)

        monthlyTransactionStatisticRepository
            .deleteAllByCurrencyProfileId(currencyProfileId)

        categoryTransactionStatisticRepository
            .deleteAllByCurrencyProfileId(currencyProfileId)
    }
}
