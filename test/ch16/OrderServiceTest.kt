package ch16

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.collections.listOf


class FakeProductRepository(val products: MutableMap<Long, Product>) : ProductRepository {
    override fun findById(id: Long): Product? = products[id]

    override fun updateStock(id: Long, newStock: Int): Boolean {
        val product = products[id]
        if (product == null) {
            return false
        }
        products[id] = product.copy(stock = newStock)
        return true
    }
}

class OrderServiceTest {
    private lateinit var repo: FakeProductRepository
    private lateinit var service: OrderService

    @BeforeEach
    fun setUp() {
        repo = FakeProductRepository(
            products = mutableMapOf(
                1L to Product(1L, "one", 100, 5),
                2L to Product(2L, "two", 150, 3),
                3L to Product(3L, "three", 200, 1),
            )
        )
        service = OrderService(repo)
    }

    @Nested
    inner class createOrder {
        @Test
        fun `정상 주문 테스트`() {
            val result = service.createdOrder(listOf(Pair(1L, 2), Pair(2L, 3)))
            assertTrue(result.isSuccess)
            val products = result.getOrNull()!!.items.map { it.product }
            assertEquals(
                listOf(
                    Product(1L, "one", 100, 3),
                    Product(2L, "two", 150, 0),
                ),
                products
            )
        }

        @ParameterizedTest(name = "{0}")
        @MethodSource("ch16.OrderServiceTest#orderFailureTestCases")
        fun `실패 테스트 케이스`(name: String, orderRequests: List<Pair<Long, Int>>, exception: Exception) {
            val result = service.createdOrder(orderRequests)
            assertTrue(result.isFailure)
            val err = result.exceptionOrNull()!!
            assertEquals(exception::class, err::class)
            assertEquals(exception.message, err.message)
        }

    }

    companion object {
        @JvmStatic
        fun orderFailureTestCases(): Stream<Arguments> = Stream.of(
            Arguments.of(
                "빈 주문 실패 테스트",
                listOf<Pair<Long, Int>>(),
                IllegalArgumentException("no order")
            ),
            Arguments.of(
                "존재하지 않는 상품 실패 테스트",
                listOf(Pair(50L, 3)),
                NoSuchElementException("product not found for id=50")
            ),
            Arguments.of("재고 부족 실패 테스트", listOf(Pair(2L, 5)), IllegalArgumentException("out of stock id=2")),
            Arguments.of("수량 0 이하 실패 테스트", listOf(Pair(1L, 0)), IllegalArgumentException("quantity must be positive")),
        )
    }
}