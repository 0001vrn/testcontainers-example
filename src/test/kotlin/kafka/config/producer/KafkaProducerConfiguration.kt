package kafka.config.producer

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import java.util.Properties

class KafkaProducerConfiguration {

    @Bean(destroyMethod = "close")
    fun produceMessage(): Producer<String, String> {
        return KafkaProducer<String, String>(producerProperties())
    }

    /**
     * Setup kafka producer configs
     * @return producer config map
     */
    private fun producerProperties(): Properties {
        val props = Properties()

        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = System.getProperty("BOOTSTRAP_SERVERS")

        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java

        return props
    }
}
