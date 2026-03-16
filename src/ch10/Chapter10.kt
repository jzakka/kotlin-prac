package ch10

class Chapter10 {
    fun example() {
        operate(3, 4) { a, b -> a + b }
        operate(3, 4) { a, b -> a * b }
        // operate(3, 4, { a, b -> a * b})

        listOf(1, 2, 3).map { it * 2 }

        listOf(1,2,3,4,5)
            .filter { it > 2 }
            .map { it * 10}
            .forEach { println(it) }

        listOf("hello", "WORLD").map(String::lowercase)

        var triple = multiplier(3)
        triple(5)
        triple(10)

        repeatInline(3) {println(it)}

        var sum = 0
        listOf(1, 2, 3).forEach { sum += it }
        println(sum)
    }
}

val double = { x: Int -> x * 2 }
fun operate(a: Int, b: Int, op: (Int, Int) -> Int): Int {
    return op(a, b)
}

fun isEven(n: Int): Boolean = n % 2==0

fun multiplier(factor: Int): (Int) -> Int {
    return { number -> number * factor }
}

fun repeat(times: Int, action: (Int) -> Unit) {
    for (i in 0 until times) action(i)
}

inline fun repeatInline(times: Int, action: (Int) -> Unit) {
    for (i in 0 until times) action(i)
}

data class Order(
    val id: Int,
    val customer: String,
    val amount: Double,
    val category: String
)

val orders = listOf(
    Order(1, "Kim", 15000.0, "food"),
    Order(2, "Lee", 82000.0, "electronics"),
    Order(3, "Kim", 3200.0, "food"),
    Order(4, "Park", 45000.0, "clothing"),
    Order(5, "Lee", 120000.0, "electronics"),
    Order(6, "Kim", 8500.0, "clothing"),
    Order(7, "Park", 67000.0, "food"),
    Order(8, "Lee", 23000.0, "food"),
)

fun totalOrderPriceByCustomer() {
    val report = orders.groupBy { it.customer }.mapValues { it.value.sumOf { e -> e.amount } }
    println(report)
}

fun averageOrderPriceByCategory() {
    val report = orders.groupBy { it.category }.mapValues { it.value.sumOf { e -> e.amount } / it.value.count() }
    println(report)
}

fun List<Order>.applyDiscount(discountRate: Double, condition: (Order) -> Boolean): List<Order> {
    return this.map { if (condition(it)) it.copy(amount = it.amount * (1 - discountRate)) else it  }
}
