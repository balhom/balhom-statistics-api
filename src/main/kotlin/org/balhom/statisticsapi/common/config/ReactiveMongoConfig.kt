package org.balhom.statisticsapi.common.config

import io.quarkus.mongodb.reactive.ReactiveMongoClient
import io.quarkus.mongodb.reactive.ReactiveMongoDatabase
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty

@ApplicationScoped
class ReactiveMongoConfig(
    private val reactiveMongoClient: ReactiveMongoClient,
    @ConfigProperty(name = "quarkus.mongodb.database") val database: String
) {
    fun getDatabase(): ReactiveMongoDatabase {
        return reactiveMongoClient
            .getDatabase(database)
    }
}