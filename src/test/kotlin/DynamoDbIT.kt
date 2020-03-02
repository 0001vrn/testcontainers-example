import helper.DynamoDbContainerConfig
import model.Todo
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import repository.TodoRepository

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DynamoDbIT {

    private lateinit var dynamoDbContainerConfig: DynamoDbContainerConfig

    private lateinit var todoRepository: TodoRepository

    @BeforeAll
    fun setUp() {
        dynamoDbContainerConfig = DynamoDbContainerConfig()
        dynamoDbContainerConfig.start()
        todoRepository = TodoRepository(dynamoDbContainerConfig.dynamoDBMapper)
    }

    @AfterAll
    fun tearDown() {
        dynamoDbContainerConfig.stop()
    }

    @BeforeEach
    fun createTable() {
        dynamoDbContainerConfig.createTable(Todo::class.java)
    }

    @AfterEach
    fun deleteTable() {
        dynamoDbContainerConfig.deleteTable(Todo::class.java)
    }

    @Test
    fun `save and get on Db 😁`() {
        val todo = Todo(12, "I will be saved and retrieved from Db")
        todoRepository.saveOrUpdate(todo)

        val todoFromDb = todoRepository.getById(todo.todoId)

        Assertions.assertNotNull(todoFromDb)
        Assertions.assertEquals(todo.todoId, todoFromDb!!.todoId)
        Assertions.assertEquals(todo.message, todoFromDb!!.message)
    }

    @Test
    fun `save and delete on Db 😧️`() {
        val todo = Todo(13, "I will be saved and deleted from Db")

        todoRepository.saveOrUpdate(todo)
        todoRepository.deleteById(todo.todoId)

        val todoFromDb = todoRepository.getById(todo.todoId)

        Assertions.assertNull(todoFromDb)
    }
}
