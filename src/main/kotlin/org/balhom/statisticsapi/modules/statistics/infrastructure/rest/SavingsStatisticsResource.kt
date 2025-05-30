package org.balhom.statisticsapi.modules.statistics.infrastructure.rest

import io.quarkus.security.Authenticated
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import org.balhom.statisticsapi.common.data.props.ObjectIdUserProps
import org.balhom.statisticsapi.modules.statistics.application.SavingsStatisticsService
import org.balhom.statisticsapi.modules.statistics.domain.models.MonthlySavingsStatistic
import org.balhom.statisticsapi.modules.statistics.domain.models.YearlySavingsStatistic
import org.balhom.statisticsapi.modules.statistics.domain.props.MonthlyStatisticsProps
import org.eclipse.microprofile.jwt.JsonWebToken
import java.util.*

@Path(SavingsStatisticsResource.RESOURCE_PATH)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
class SavingsStatisticsResource(
    private val service: SavingsStatisticsService
) {
    companion object {
        const val RESOURCE_PATH = "/api/v1/savings"

        const val GET_MONTHLY_PATH = "/monthly"
    }

    @Inject
    lateinit var jwt: JsonWebToken

    @GET
    @Path(GET_MONTHLY_PATH)
    fun getMonthlySavingsStatistics(
        @QueryParam("currencyProfileId") currencyProfileId: UUID,
        @QueryParam("year") year: Int
    ): Uni<List<MonthlySavingsStatistic>> {
        val currencyProfileIdAndUser = ObjectIdUserProps(
            currencyProfileId,
            UUID.fromString(jwt.subject)
        )

        return service.getMonthlyStatistics(
            MonthlyStatisticsProps(
                currencyProfileIdAndUser,
                year
            )
        )
    }

    @GET
    @Path("/yearly")
    fun getYearlySavingsStatistics(
        @QueryParam("currencyProfileId") currencyProfileId: UUID
    ): Uni<List<YearlySavingsStatistic>> {
        val currencyProfileIdAndUser = ObjectIdUserProps(
            currencyProfileId,
            UUID.fromString(jwt.subject)
        )

        return service.getYearlyStatistics(
            currencyProfileIdAndUser
        )
    }
}