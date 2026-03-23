package ch16

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Calculator {
    fun add(a: Int, b: Int): Int = a + b
    fun divide(a: Int, b: Int): Int {
        require(b != 0) {
            "cannot divide by zero"
        }
        return a / b
    }
}

class CalculatorTest {
    private val calc = Calculator()

    @Test
    fun `2 더하기 3은 5`() {
        assertEquals(5, calc.add(2, 3))
    }

    @Test
    fun `0으로 나누면 예외발생`() {
        val exception = assertThrows<IllegalArgumentException> {
            calc.divide(10, 0)
        }
        assertEquals("cannot divide by zero", exception.message)
    }

    @Test
    fun `음수 더하기`() {
        assertAll(
            { assertEquals(-1, calc.add(2, -3)) },
            { assertEquals(0, calc.add(-5, 5)) },
            { assertEquals(-8, calc.add(-3, -5)) },
        )
    }
}

class LifeCycleTest {
    companion object {
        @JvmStatic
        @BeforeAll
        fun setupAll() {
            println("모든 테스트 전 1회 샐힝")
        }

        @JvmStatic
        @AfterAll
        fun teardownAll() {
            println("모든 테스트 후 1회 실행")
        }
    }

    @BeforeEach
    fun setup() {
        println("각 테스트 전 실행")
    }

    @AfterEach
    fun teardown() {
        println("각 테스트 후 실행")
    }

    @Test
    fun `테스트 1`() {
        println("테스트 1 실행")
    }

    @Test
    fun `테스트 2`() {
        println("테스트 2 실행")
    }
}

class ParameterizedTests {
    private val calc = Calculator()

    @ParameterizedTest(name = "add({0}, {1}) = {2}")
    @CsvSource(
        "2, 3, 5",
        "-1, -2, -3",
        "0, 0, 0",
        "100, -100, 0"
    )
    fun `CSV 기반 덧셈 테스트`(a: Int, b: Int, expected: Int) {
        assertEquals(expected, calc.add(a, b))
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("divideTestCases")
    fun `나눗셈 테스트`(name: String, a: Int, b: Int, expected: Int) {
        assertEquals(expected, calc.divide(a, b))
    }

    companion object {
        @JvmStatic
        fun divideTestCases(): Stream<Arguments> = Stream.of(
            Arguments.of("10 / 2 = 5", 10, 2, 5),
            Arguments.of("9 / 3 = 3", 9, 3, 3),
            Arguments.of("7 / 2 = 3 (정수 나눗셈)", 7, 2, 3),
        )
    }
}

@DisplayName("Calculator")
class NestedTest {
    private val calc = Calculator()

    @Nested
    @DisplayName("add 메서드")
    inner class Add {
        @Test
        fun `양수끼리 더하기`() {
            assertEquals(5, calc.add(2, 3))
        }

        @Test
        fun `음수끼리 더하기`() {
            assertEquals(-5, calc.add(-2, -3))
        }
    }

    @Nested
    @DisplayName("divide 메서드")
    inner class Divide {
        @Test
        fun `정상 나눗셈`() {
            assertEquals(5, calc.divide(10, 2))
        }

        @Test
        fun `0으로 나누기` () {
            assertThrows<IllegalArgumentException> {
                calc.divide(1, 0)
            }
        }
    }
}

class FakeUserRepository: UserRepository {
    private val users = mutableMapOf<Long, User>()
    private var nextId = 1L

    override fun findBy(id: Long): User? = users[id]

    override fun addUser(user: User): User {
        val saved = user.copy(id = nextId++)
        users[saved.id] = saved
        return saved
    }

    override fun existsByEmail(email: String): Boolean = users.values.any { it.email == email }
}

class UserServiceTest {
    private lateinit var repo: FakeUserRepository
    private lateinit var service: UserService

    @BeforeEach
    fun setup() {
        repo = FakeUserRepository()
        service = UserService(repo)
    }

    @Nested
    inner class GetUser {
        @Test
        fun `존재하는 사용자 조회 성공`() {
            repo.addUser(User(1, "Alice", "alice@test.com"))

            val result = service.getUser(1)

            assertTrue(result.isSuccess)
            assertEquals("Alice", result.getOrNull()?.name)
        }

        @Test
        fun `존재하지 않는 사용자 조회 실패`() {
            val result = service.getUser(999)

            assertTrue(result.isFailure)
            assertInstanceOf(
                NoSuchElementException::class.java,
                result.exceptionOrNull() as? NoSuchElementException
            )
        }
    }

    @Nested
    inner class CreateUser {
        @Test
        fun `정상적인 사용자 생성`() {
            val result = service.createUser("Bob", "bob@test.com")

            assertTrue(result.isSuccess)
            val user = result.getOrNull()!!
            assertEquals("Bob", user.name)
            assertEquals(1L, user.id)
        }

        @Test
        fun `빈 이름으로 생성 실패`() {
            val result = service.createUser("", "test@test.com")

            assertTrue(result.isFailure)
            assertInstanceOf(
                IllegalArgumentException::class.java,
                result.exceptionOrNull()
            )
        }

        @Test
        fun `중복 이메일로 생성 실패`() {
            service.createUser("Alice", "alice@test.com")

            val result = service.createUser("Bob", "alice@test.com")

            assertTrue(result.isFailure)
            assertEquals(
                "User with email alice@test.com already exists",
                result.exceptionOrNull()?.message
            )
        }
    }
}

class CalculatorStringSpec: StringSpec({
    val calc = Calculator()

    "2 + 3 = 5" {
        calc.add(2, 3) shouldBe 5
    }

    "0으로 나누면 예외" {
        shouldThrow<IllegalArgumentException> {
            calc.divide(1, 0)
        }
    }
})

class UserServiceBehaviorSpec: BehaviorSpec({
    given("등록된 사용자가 있을 떄") {
        val repo = FakeUserRepository()
        repo.addUser(User(1, "Alice", "alice@test.com"))
        val service = UserService(repo)

        `when`("해당 ID로 조회하면") {
            val result = service.getUser(1)

            then("사용자 정보가 반환된다") {
                result.isSuccess shouldBe true
                result.getOrNull()?.name shouldBe "Alice"
            }
        }

        `when`("존재하지 않는 ID로 조회하면") {
            val result = service.getUser(999)

            then("실패결과가 반환된다") {
                result.isFailure shouldBe true
            }
        }
    }
})

