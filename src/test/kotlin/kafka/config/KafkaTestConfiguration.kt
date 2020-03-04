package kafka.config

import kafka.config.consumer.KafkaConsumerConfiguration
import kafka.config.producer.KafkaProducerConfiguration
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.producer.Producer
import org.springframework.context.annotation.Bean

class KafkaTestConfiguration {

    @Bean
    fun kafkaProducerConfig(): KafkaProducerConfiguration {
        return KafkaProducerConfiguration()
    }

    @Bean
    fun kafkaConsumerConfig(): KafkaConsumerConfiguration {
        return KafkaConsumerConfiguration()
    }

    @Bean
    fun produceMessage(kafkaProducerConfiguration: KafkaProducerConfiguration): Producer<String, String> {
        return kafkaProducerConfiguration.produceMessage()
    }

    @Bean
    fun consumeMessage(kafkaConsumerConfiguration: KafkaConsumerConfiguration): Consumer<String, String> {
        return kafkaConsumerConfiguration.consumeMessage()
    }
}
