package kafka

import org.junit.jupiter.api.Test

class KafkaTopicIT : AbstractKafkaTest() {

    @Test
    fun `Push message in sample_topic topic and consume it 😁`() {
        val messageToBeSent = "I will be sent to the mentioned topic"
        val topicName = "sample_topic"
        sendMessageToTopic(topicName, "12", messageToBeSent)

        var messageReceived = consumeMessageFromTopic(topicName)
        assert(messageReceived.contains(messageToBeSent))
    }
}
