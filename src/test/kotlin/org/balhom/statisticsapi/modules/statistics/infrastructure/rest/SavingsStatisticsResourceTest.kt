package org.balhom.statisticsapi.modules.statistics.infrastructure.rest

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.security.TestSecurity
import io.restassured.RestAssured.given
import io.smallrye.mutiny.Uni
import org.balhom.statisticsapi.common.containers.BaseTestContainersTest
import org.balhom.statisticsapi.common.data.props.ObjectIdUserProps
import org.balhom.statisticsapi.common.utils.TestDataUtils.Companion.randomInt
import org.balhom.statisticsapi.modules.statistics.application.SavingsStatisticsService
import org.balhom.statisticsapi.modules.statistics.domain.models.MockMonthlySavingsStatisticFactory
import org.balhom.statisticsapi.modules.statistics.domain.props.MonthlyStatisticsProps
import org.eclipse.microprofile.jwt.JsonWebToken
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*


@QuarkusTest
class SavingsStatisticsResourceTest : BaseTestContainersTest() {

    @InjectMock
    lateinit var savingsStatisticsService: SavingsStatisticsService

    @InjectMock
    lateinit var jwt: JsonWebToken

    private final val mapper = jacksonObjectMapper()

    init {
        mapper.findAndRegisterModules()
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    fun `get all MonthlySavingsStatistics should return list of profiles responses`() {
        val userId = UUID.randomUUID()
        val currencyProfileId = UUID.randomUUID()
        val currencyProfileIdProps = ObjectIdUserProps(
            currencyProfileId,
            userId
        )
        val year = randomInt(0, 5000)

        val props = MonthlyStatisticsProps(
            currencyProfileIdProps,
            year
        )

        val mockList = listOf(
            MockMonthlySavingsStatisticFactory.create()
        )

        Mockito
            .`when`(jwt.subject)
            .thenReturn(userId.toString())

        Mockito
            .`when`(savingsStatisticsService.getMonthlyStatistics(props))
            .thenReturn(
                Uni.createFrom()
                    .item(mockList)
            )

        val result = given()
            .`when`()
            .get(
                SavingsStatisticsResource.RESOURCE_PATH
                        + SavingsStatisticsResource.GET_MONTHLY_PATH
                        + "?currencyProfileId=${currencyProfileId}&year=${year}"
            )
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString()

        assertEquals(mapper.writeValueAsString(mockList), result)
    }
}