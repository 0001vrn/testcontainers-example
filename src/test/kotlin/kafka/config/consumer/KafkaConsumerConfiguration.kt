package kafka.config.consumer

import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import java.util.Properties

class KafkaConsumerConfiguration {

    @Bean(destroyMethod = "close")
    fun consumeMessage(): Consumer<String, String> {
        return KafkaConsumer<String, String>(consumerProperties())
    }

    /**
     * Setup kafka consumer configs
     * @return consumer config map
     */
    private fun consumerProperties(): Properties {
        val props = Properties()

        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = System.getProperty("BOOTSTRAP_SERVERS")

        props[ConsumerConfig.GROUP_ID_CONFIG] = System.getProperty("GROUP_ID")
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = OffsetResetStrategy.EARLIEST.name.toLowerCase()

        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java

        return props
    }
}
