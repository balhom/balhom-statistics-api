package org.balhom.statisticsapi.modules.statistics.application

import org.balhom.statisticsapi.common.data.enums.EventChangeTypeEnum
import org.balhom.statisticsapi.modules.statistics.domain.props.MockTransactionChangePropsFactory
import org.balhom.statisticsapi.modules.statistics.domain.props.SavingsStatisticToAddProps
import org.balhom.statisticsapi.modules.statistics.domain.props.TransactionStatisticToAddProps
import org.balhom.statisticsapi.modules.transactionchanges.application.TransactionChangesService
import org.balhom.statisticsapi.modules.transactionchanges.domain.enums.TransactionTypeEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.kotlin.capture
import java.math.BigDecimal

class TransactionChangesServiceTest {

    private lateinit var transactionStatisticsService: TransactionStatisticsService
    private lateinit var savingsStatisticsService: SavingsStatisticsService

    private lateinit var transactionChangesService: TransactionChangesService

    @BeforeEach
    fun setUp() {
        transactionStatisticsService = mock(
            TransactionStatisticsService::class.java
        )
        savingsStatisticsService = mock(
            SavingsStatisticsService::class.java
        )

        transactionChangesService = TransactionChangesService(
            transactionStatisticsService,
            savingsStatisticsService
        )
    }

    @Test
    fun `processChange should handle income CREATE correctly`() {
        val props = MockTransactionChangePropsFactory.create(
            eventChangeType = EventChangeTypeEnum.CREATE,
            type = TransactionTypeEnum.INCOME,
        )

        val transactionStatisticCaptor = ArgumentCaptor.forClass(
            TransactionStatisticToAddProps::class.java
        )
        val savingsStatisticCaptor = ArgumentCaptor.forClass(
            SavingsStatisticToAddProps::class.java
        )

        doNothing()
            .`when`(transactionStatisticsService)
            .add(capture<TransactionStatisticToAddProps>(transactionStatisticCaptor))
        doNothing()
            .`when`(savingsStatisticsService)
            .add(capture<SavingsStatisticToAddProps>(savingsStatisticCaptor))

        transactionChangesService.processChange(props)

        with(transactionStatisticCaptor.value) {
            assertEquals(props.currencyProfileId, currencyProfileId)
            assertEquals(props.date, date)
            assertNull(oldDate)
            assertEquals(props.category, category)
            assertNull(oldCategory)
            assertEquals(props.amount, incomeToAdd)
            assertNull(oldIncomeAdded)
            assertEquals(BigDecimal(0.0), expensesToAdd)
            assertNull(oldExpensesAdded)
        }

        with(savingsStatisticCaptor.value) {
            assertEquals(props.currencyProfileId, currencyProfileId)
            assertEquals(props.date, date)
            assertNull(oldDate)
            assertEquals(props.amount, amountToAdd)
            assertNull(oldAmountAdded)
            assertEquals(props.cpGoalMonthlySaving, monthlyGoal)
            assertEquals(props.cpGoalYearlySaving, yearlyGoal)
        }
    }

    @Test
    fun `processChange should handle expense CREATE correctly`() {
        val props = MockTransactionChangePropsFactory.create(
            eventChangeType = EventChangeTypeEnum.CREATE,
            type = TransactionTypeEnum.EXPENSE,
        )

        val transactionStatisticCaptor = ArgumentCaptor.forClass(
            TransactionStatisticToAddProps::class.java
        )
        val savingsStatisticCaptor = ArgumentCaptor.forClass(
            SavingsStatisticToAddProps::class.java
        )

        doNothing()
            .`when`(transactionStatisticsService)
            .add(capture<TransactionStatisticToAddProps>(transactionStatisticCaptor))
        doNothing()
            .`when`(savingsStatisticsService)
            .add(capture<SavingsStatisticToAddProps>(savingsStatisticCaptor))

        transactionChangesService.processChange(props)

        with(transactionStatisticCaptor.value) {
            assertEquals(props.currencyProfileId, currencyProfileId)
            assertEquals(props.date, date)
            assertNull(oldDate)
            assertEquals(props.category, category)
            assertNull(oldCategory)
            assertEquals(BigDecimal(0.0), incomeToAdd)
            assertNull(oldIncomeAdded)
            assertEquals(props.amount, expensesToAdd)
            assertNull(oldExpensesAdded)
        }

        with(savingsStatisticCaptor.value) {
            assertEquals(props.currencyProfileId, currencyProfileId)
            assertEquals(props.date, date)
            assertNull(oldDate)
            assertEquals(props.amount.negate(), amountToAdd)
            assertNull(oldAmountAdded)
            assertEquals(props.cpGoalMonthlySaving, monthlyGoal)
            assertEquals(props.cpGoalYearlySaving, yearlyGoal)
        }
    }

    @Test
    fun `processChange should handle income UPDATE correctly`() {
        val props = MockTransactionChangePropsFactory.create(
            eventChangeType = EventChangeTypeEnum.UPDATE,
            type = TransactionTypeEnum.INCOME,
        )

        val transactionStatisticCaptor = ArgumentCaptor.forClass(TransactionStatisticToAddProps::class.java)
        val savingsStatisticCaptor = ArgumentCaptor.forClass(SavingsStatisticToAddProps::class.java)

        doNothing()
            .`when`(transactionStatisticsService)
            .add(capture<TransactionStatisticToAddProps>(transactionStatisticCaptor))
        doNothing()
            .`when`(savingsStatisticsService)
            .add(capture<SavingsStatisticToAddProps>(savingsStatisticCaptor))

        transactionChangesService.processChange(props)

        with(transactionStatisticCaptor.value) {
            assertEquals(props.date, date)
            assertEquals(props.oldData?.oldDate, oldDate)
            assertEquals(props.category, category)
            assertEquals(props.oldData?.oldCategory, oldCategory)
            assertEquals(props.amount, incomeToAdd)
            assertEquals(props.oldData?.oldAmount, oldIncomeAdded)
            assertEquals(BigDecimal(0.0), expensesToAdd)
            assertNull(oldExpensesAdded)
        }

        with(savingsStatisticCaptor.value) {
            assertEquals(props.date, date)
            assertEquals(props.oldData?.oldDate, oldDate)
            assertEquals(props.amount, amountToAdd)
            assertEquals(props.oldData?.oldAmount, oldAmountAdded)
            assertEquals(props.cpGoalMonthlySaving, monthlyGoal)
            assertEquals(props.cpGoalYearlySaving, yearlyGoal)
        }
    }

    @Test
    fun `processChange should handle expense UPDATE correctly`() {
        val props = MockTransactionChangePropsFactory.create(
            eventChangeType = EventChangeTypeEnum.UPDATE,
            type = TransactionTypeEnum.EXPENSE,
        )

        val transactionStatisticCaptor = ArgumentCaptor
            .forClass(TransactionStatisticToAddProps::class.java)
        val savingsStatisticCaptor = ArgumentCaptor
            .forClass(SavingsStatisticToAddProps::class.java)

        doNothing()
            .`when`(transactionStatisticsService)
            .add(capture<TransactionStatisticToAddProps>(transactionStatisticCaptor))
        doNothing()
            .`when`(savingsStatisticsService)
            .add(capture<SavingsStatisticToAddProps>(savingsStatisticCaptor))

        transactionChangesService.processChange(props)

        with(transactionStatisticCaptor.value) {
            assertEquals(props.date, date)
            assertEquals(props.oldData?.oldDate, oldDate)
            assertEquals(props.category, category)
            assertEquals(props.oldData?.oldCategory, oldCategory)
            assertEquals(BigDecimal(0.0), incomeToAdd)
            assertNull(oldIncomeAdded)
            assertEquals(props.amount, expensesToAdd)
            assertEquals(props.oldData?.oldAmount, oldExpensesAdded)
        }

        with(savingsStatisticCaptor.value) {
            assertEquals(props.date, date)
            assertEquals(props.oldData?.oldDate, oldDate)
            assertEquals(props.amount.negate(), amountToAdd)
            assertEquals(props.oldData?.oldAmount?.negate(), oldAmountAdded)
            assertEquals(props.cpGoalMonthlySaving, monthlyGoal)
            assertEquals(props.cpGoalYearlySaving, yearlyGoal)
        }
    }

    @Test
    fun `processChange should handle income DELETE correctly`() {
        val props = MockTransactionChangePropsFactory.create(
            eventChangeType = EventChangeTypeEnum.DELETE,
            type = TransactionTypeEnum.INCOME
        )

        val transactionStatisticCaptor = ArgumentCaptor.forClass(
            TransactionStatisticToAddProps::class.java
        )
        val savingsStatisticCaptor = ArgumentCaptor.forClass(
            SavingsStatisticToAddProps::class.java
        )

        doNothing()
            .`when`(transactionStatisticsService)
            .add(capture<TransactionStatisticToAddProps>(transactionStatisticCaptor))
        doNothing()
            .`when`(savingsStatisticsService)
            .add(capture<SavingsStatisticToAddProps>(savingsStatisticCaptor))

        transactionChangesService.processChange(props)

        with(transactionStatisticCaptor.value) {
            assertEquals(props.date, date)
            assertEquals(props.oldData?.oldDate, oldDate)
            assertEquals(props.category, category)
            assertEquals(props.oldData?.oldCategory, oldCategory)
            assertEquals(props.amount.negate(), incomeToAdd)
            assertNull(oldIncomeAdded)
            assertEquals(BigDecimal(0.0), expensesToAdd)
            assertNull(oldExpensesAdded)
        }

        with(savingsStatisticCaptor.value) {
            assertEquals(props.date, date)
            assertEquals(props.oldData?.oldDate, oldDate)
            assertEquals(props.amount.negate(), amountToAdd)
            assertNull(oldAmountAdded)
        }
    }

    @Test
    fun `processChange should handle expense DELETE correctly`() {
        val props = MockTransactionChangePropsFactory.create(
            eventChangeType = EventChangeTypeEnum.DELETE,
            type = TransactionTypeEnum.EXPENSE,
        )

        val transactionStatisticCaptor = ArgumentCaptor.forClass(
            TransactionStatisticToAddProps::class.java
        )
        val savingsStatisticCaptor = ArgumentCaptor.forClass(
            SavingsStatisticToAddProps::class.java
        )

        doNothing()
            .`when`(transactionStatisticsService)
            .add(capture<TransactionStatisticToAddProps>(transactionStatisticCaptor))
        doNothing()
            .`when`(savingsStatisticsService)
            .add(capture<SavingsStatisticToAddProps>(savingsStatisticCaptor))

        transactionChangesService.processChange(props)

        with(transactionStatisticCaptor.value) {
            assertEquals(props.date, date)
            assertEquals(props.oldData?.oldDate, oldDate)
            assertEquals(props.category, category)
            assertEquals(props.oldData?.oldCategory, oldCategory)
            assertEquals(BigDecimal(0.0), incomeToAdd)
            assertNull(oldIncomeAdded)
            assertEquals(props.amount.negate(), expensesToAdd)
            assertNull(oldExpensesAdded)
        }

        with(savingsStatisticCaptor.value) {
            assertEquals(props.date, date)
            assertEquals(props.oldData?.oldDate, oldDate)
            assertEquals(props.amount, amountToAdd)
            assertNull(oldAmountAdded)
        }
    }
}
