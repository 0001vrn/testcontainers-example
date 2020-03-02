package repository

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import model.Todo

class TodoRepository(private val dynamoDBMapper: DynamoDBMapper) {

    fun getById(todoId: Long): Todo? {
        return dynamoDBMapper.load(Todo(todoId))
    }

    fun saveOrUpdate(todo: Todo) {
        dynamoDBMapper.save(todo)
    }

    fun deleteById(todoId: Long) {
        dynamoDBMapper.delete(Todo(todoId))
    }
}
