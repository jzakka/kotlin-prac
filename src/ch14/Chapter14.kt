package ch14

fun divide(a: Int, b: Int): Int {
    if (b == 0) throw IllegalArgumentException("Cannot divide by zero")
    return a / b
}

//fun main() {
//    try {
//        val result = divide(10, 0)
//        println("result: $result")
//    } catch (e: IllegalArgumentException) {
//        println("Catched exception: ${e.message}")
//    } catch (e: Exception) {
//        println("other exception: ${e.message}")
//    } finally {
//        println("finally")
//    }
//}

//fun main() {
//    val result = try{
//        divide(10, 0)
//    } catch (e: Exception){
//        -1
//    }
//    println("result=$result")
//}

data class User(val name: String, val email: String)

fun findUser(id: Int): Result<User> {
    if (id <= 0) return Result.failure(IllegalArgumentException("invalid id: $id"))
    return Result.success(User("Alice", "alice@test.com"))
}

//fun main() {
//    val success = findUser(1)
//    val failure = findUser(-1)
//
//    println(success.isSuccess)
//    println(success.getOrNull())
//    println(failure.isFailure)
//    println(failure.exceptionOrNull()?.message)
//
//    val user = failure.getOrDefault(User("Unknown", ""))
//    println(user)
//
//    val user2 = failure.getOrElse { e ->
//        println("에러 발생: ${e.message}")
//        User("Fallback", "")
//    }
//    println(user2)
//}

fun riskyOperation(input: String): Int {
    return input.toInt()
}

//fun main() {
//    val result1 = runCatching { riskyOperation("42") }
//    val result2 = runCatching { riskyOperation("abc") }
//
//    println(result1.getOrNull())
//    println(result2.getOrNull())
//
//    val doubled = result1.map { it * 2}
//    println(doubled.getOrElse { 3 })
//
//    val recovered = result2.recover {e ->
//        println("recover: ${e.message}")
//        0
//    }
//    println(recovered.getOrNull())
//
//    val message = result2.fold(
//        onSuccess = {"val:$it"},
//        onFailure = {"error: ${it.message}"}
//    )
//    println(message)
//}

fun parsePort(input: String): Result<Int> = runCatching {
    val port = input.toInt()
    require(port in 1..65535)
    port
}

fun connectToServer(host: String, port: Int): Result<String> = runCatching {
    if (host == "localhost") " Connected to $host:$port"
    else throw IllegalArgumentException("host $host is not a valid host")
}

//fun main() {
//    val result = parsePort("8080")
//        .mapCatching { port -> connectToServer("localhost", port).getOrThrow() }
//        .mapCatching { connection -> "$connection - ready" }
//    println(result)
//
//    val failed = parsePort("99999")
//        .mapCatching { port -> connectToServer("localhost", port).getOrThrow() }
//        .mapCatching { connection -> "$connection - ready" }
//    println(failed)
//
//    failed
//        .onSuccess { println("success: $it") }
//        .onFailure { println("failure: ${it.message}") }
//}

sealed class AppException(message: String) : Exception(message) {
    class NotFound(val id: Int) : AppException("ID $id not found")
    class Unauthorized(val user: String) : AppException("$user is unauthorized")
    class Validation(val field: String, val reason: String) : AppException("$field: $reason")
}

fun findOrder(id: Int): Result<String> {
    if (id <= 0) return Result.failure(AppException.Validation("id", "should be positive"))
    if (id == 999) return Result.failure(AppException.NotFound(id))
    return Result.success("Order #$id")
}

//fun main() {
//    listOf(1, -1, 999).forEach { id ->
//        val result = findOrder(id)
//
//        result.fold(
//            onSuccess = {println("success: $it")},
//            onFailure = { e->
//                when (e) {
//                    is AppException.NotFound -> println("404: ${e.message}")
//                    is AppException.Unauthorized -> println("401: ${e.message}")
//                    is AppException.Validation -> println("400: ${e.message}")
//                    else -> println("unknown error: ${e.message}")
//                }
//            }
//        )
//    }
//}

sealed class OrderException(message: String) : Exception(message) {
    class InvalidOrder(reason: String) : OrderException("Validation Failed: $reason")
    class OutOfStock(itemId: String) : OrderException("Out of stock: #$itemId")
    class PaymentFailed(amount: Int, reason: String) : OrderException("Failed to payment($amount, $reason)")
    class ShippingError(address: String) : OrderException("Shipping failed: $address")
}

data class Order(val id: Int, val itemId: String, val quantity: Int, val amount: Int, val address: String)

fun validateOrder(order: Order): Result<Order> {
    if (order.quantity <= 0) {
        return Result.failure(OrderException.InvalidOrder("Quantity must be greater than zero"))
    }
    return Result.success(order)
}

fun checkStock(order: Order): Result<Order> {
    if (order.itemId == "DELETED") {
        return Result.failure(OrderException.OutOfStock(order.itemId))
    }
    return Result.success(order)
}

fun processPayment(order: Order): Result<Order> {
    if (order.amount > 100000) {
        return Result.failure(OrderException.PaymentFailed(order.amount, "한도 초과"))
    }
    return Result.success(order)
}

fun shipOrder(order: Order): Result<Order> {
    if (order.address.isEmpty()) {
        return Result.failure(OrderException.ShippingError(order.address))
    }
    return Result.success(order)
}

fun main() {
    val orders = listOf(
        Order(1, "ITEM-A", 2, 5000, "서울시"),
        Order(2, "ITEM-B", 0, 3000, "부산시"),
        Order(3, "DELETED", 1, 2000, "대전시"),
        Order(4, "ITEM-C", 1, 50000, "광주시"),
        Order(5, "ITEM-D", 1, 1000, ""),
    )

    orders.forEach { e ->
        validateOrder(e)
            .mapCatching { checkStock(it).getOrThrow() }
            .mapCatching { processPayment(it).getOrThrow() }
            .mapCatching { shipOrder(it).getOrThrow() }
            .fold(
                onSuccess = { println("ship order successful: $it") },
                onFailure = { println("ship order failed: $it") }
            )
    }
}
