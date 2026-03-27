package effectivekotlin

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock

data class UserDao(val id: String)
class UserController(dao1: UserDao) {
    fun doSomething() {}
}

class UserControllerTest {
    private lateinit var dao: UserDao
    private lateinit var controller: UserController

    @BeforeEach
    fun init() {
        dao = mock()
        controller = UserController(dao)
    }

    @Test
    fun test() {
        controller.doSomething()
    }
}