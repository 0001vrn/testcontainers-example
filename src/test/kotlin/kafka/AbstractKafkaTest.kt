package kafka

import kafka.config.KafkaContainerConfiguration
import kafka.config.KafkaTestConfiguration
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.rnorth.ducttape.unreliables.Unreliables
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.Duration
import java.util.concurrent.TimeUnit

@ExtendWith(SpringExtension::class)
@ContextConfiguration(initializers = [
    KafkaContainerConfiguration.Initializer::class
], classes = [
    KafkaTestConfiguration::class
])
abstract class AbstractKafkaTest {

    @Autowired
    private lateinit var kafkaProducer: Producer<String, String>

    @Autowired
    private lateinit var kafkaConsumer: Consumer<String, String>

    fun consumeMessageFromTopic(topicName: String): String {

        kafkaConsumer.subscribe(listOf(topicName))

        var message = ""

        Unreliables.retryUntilTrue(10, TimeUnit.SECONDS) {
            var records = kafkaConsumer.poll(Duration.ofSeconds(1))
            message = records.single().value()
            records.count() == 1
        }

        kafkaConsumer.unsubscribe()

        return message
    }

    fun sendMessageToTopic(topicName: String, key: String, value: String) {

        kafkaProducer.send(ProducerRecord(
                topicName,
                key,
                value
        )
        ).get()
    }
}
