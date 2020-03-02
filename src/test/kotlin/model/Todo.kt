package model

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable

@DynamoDBTable(tableName = "todo")
data class Todo constructor(
        @DynamoDBHashKey(attributeName = "todo_id")
        var todoId: Long,
        @DynamoDBAttribute(attributeName = "message")
        var message: String
) {
    constructor() : this(
        todoId = -1,
        message = "empty constructor"
    )

    constructor(todoId: Long) : this(
        todoId = todoId,
        message = ""
    )
}
