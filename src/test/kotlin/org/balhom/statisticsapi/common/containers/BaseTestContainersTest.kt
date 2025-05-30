package org.balhom.statisticsapi.common.containers

import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.kafka.KafkaContainer
import org.testcontainers.utility.DockerImageName

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class BaseTestContainersTest {

    companion object {
        private val mongoContainer = MongoDBContainer(
            DockerImageName.parse("mongo:5.0")
        )

        private val kafkaContainer = KafkaContainer(
            DockerImageName.parse(
                "apache/kafka:3.7.2"
            )
        )

        @BeforeAll
        @JvmStatic
        fun startContainers() {
            mongoContainer.start()
            kafkaContainer.start()

            // MongoDB Section
            System.setProperty(
                "quarkus.mongodb.connection-string",
                mongoContainer.replicaSetUrl
            )

            // Kafka Section
            System.setProperty(
                "kafka.bootstrap.servers",
                kafkaContainer.bootstrapServers
            )
        }

        @AfterAll
        @JvmStatic
        fun stopContainers() {
            // Avoid stop for reuse
        }
    }
}