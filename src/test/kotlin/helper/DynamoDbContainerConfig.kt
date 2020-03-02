package helper

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput
import extension.KGenericContainer

class DynamoDbContainerConfig {
    lateinit var dynamoDBMapper: DynamoDBMapper
    private lateinit var amazonDynamoDB: AmazonDynamoDB
    private var dynamoContainer: KGenericContainer = genericContainer()

    private fun genericContainer(): KGenericContainer {
        val dynamoDocker = KGenericContainer("amazon/dynamodb-local:1.12.0")
                .withExposedPorts(8000)
        dynamoDocker.setCommand("-jar DynamoDBLocal.jar -sharedDb")
        dynamoDocker.start()
        return dynamoDocker
    }

    private fun awsDynamoDbClient(dynamoDB: KGenericContainer): AmazonDynamoDB {
        val endpoint = "http://" + dynamoDB.containerIpAddress + ":" +
                dynamoDB.getMappedPort(8000)
        val dynamoDbBuilder: AmazonDynamoDBAsyncClientBuilder = AmazonDynamoDBAsyncClientBuilder.standard()
                .withEndpointConfiguration(
                        AwsClientBuilder.EndpointConfiguration(endpoint, "local"))
                // Dummy credentials are necessary
                .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials("acc", "sec")))
        return dynamoDbBuilder.build()
    }

    fun start() {
        dynamoContainer.setCommand("-jar DynamoDBLocal.jar -sharedDb")
        dynamoContainer.start()

        amazonDynamoDB = awsDynamoDbClient(dynamoContainer)
        dynamoDBMapper = DynamoDBMapper(amazonDynamoDB)
    }

    fun stop() {
        dynamoContainer.stop()
    }

    fun createTable(clazz: Class<*>) {
        val createTableRequest = dynamoDBMapper.generateCreateTableRequest(clazz)
        createTableRequest.provisionedThroughput = ProvisionedThroughput().withReadCapacityUnits(1)
                .withWriteCapacityUnits(1)

        amazonDynamoDB.createTable(createTableRequest)
    }

    fun deleteTable(clazz: Class<*>) {
        val deleteRequest = dynamoDBMapper.generateDeleteTableRequest(clazz)
        amazonDynamoDB.deleteTable(deleteRequest)
    }

}
