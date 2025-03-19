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
import org.balhom.statisticsapi.modules.statistics.application.TransactionStatisticsService
import org.balhom.statisticsapi.modules.statistics.domain.models.DailyTransactionStatistic
import org.balhom.statisticsapi.modules.statistics.domain.models.MonthlyTransactionStatistic
import org.balhom.statisticsapi.modules.statistics.domain.props.DailyStatisticsProps
import org.balhom.statisticsapi.modules.statistics.domain.props.MonthlyStatisticsProps
import org.eclipse.microprofile.jwt.JsonWebToken
import java.util.UUID

@Path(TransactionStatisticsResource.RESOURCE_PATH)
@Consumes(MediaType.MULTIPART_FORM_DATA)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
class TransactionStatisticsResource(
    private val service: TransactionStatisticsService
) {
    companion object {
        const val RESOURCE_PATH = "/api/v1/transactions"
    }

    @Inject
    lateinit var jwt: JsonWebToken

    @GET
    @Path("/monthly")
    fun getMonthlyTransactionStatistics(
        @QueryParam("year") year: Int
    ): Uni<List<MonthlyTransactionStatistic>> {
        return service.getMonthlyStatistics(
            MonthlyStatisticsProps(
                UUID.fromString(jwt.subject),
                year
            )
        )
    }

    @GET
    @Path("/daily")
    fun getDailyTransactionStatistics(
        @QueryParam("month") month: Int,
        @QueryParam("year") year: Int
    ): Uni<List<DailyTransactionStatistic>> {
        return service.getDailyStatistics(
            DailyStatisticsProps(
                UUID.fromString(jwt.subject),
                month,
                year
            )
        )
    }
}