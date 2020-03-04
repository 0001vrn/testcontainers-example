package kafka.config

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.KafkaContainer

object KafkaContainerConfiguration {

    private lateinit var kafkaContainer: KafkaContainer

    init {
        startKafka()
    }

    private fun startKafka() {
        kafkaContainer = KafkaContainer()
            .withNetworkAliases("broker")
        kafkaContainer.start()
    }

    class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            val bootstrapServers = kafkaContainer.bootstrapServers
            System.setProperty("BOOTSTRAP_SERVERS", bootstrapServers)
            System.setProperty("GROUP_ID", "SAMPLE_CONSUMER_GROUP_ID")
        }
    }
}
