package ch12

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

suspend fun fetchData(): String {
    delay(1000)
    return "data"
}

//fun main() = runBlocking {
//    val result = fetchData()
//    println(result)
//}

//fun main() = runBlocking {
//    val job = launch {
//        println("코루탄 시작")
//        delay(1000)
//        println("코루틴 완료")
//    }
//    println("메인 계속 실행")
//    job.join()
//}

suspend fun fetchUser(): String {
    delay(500)
    return "Alice"
}

suspend fun fetchOrder(): String {
    delay(700)
    return "Order #1234"
}

//fun main() = runBlocking {
//    val user = async { fetchUser() }
//    val order = async { fetchOrder() }
//    println("user=${user.await()} order=${order.await()}")
//}

suspend fun doTasks() = coroutineScope {
    launch {
        delay(300)
        println("Task A 완료")
    }
    launch {
        delay(200)
        println("Task B 완료")
    }
}
//
//fun main() = runBlocking {
//    doTasks()
//    println("모든 테스크 완료")
//}

//fun main() = runBlocking {
//    val job = launch {
//        var count = 0
//        while(isActive) {
//            count++
//            println("working... ($count")
//            delay(200)
//        }
//    }
//    delay(550)
//    job.cancelAndJoin()
//    println("취소 완료")
//}

//fun main() = runBlocking {
//    val result = withTimeoutOrNull(300) {
//        delay(500)
//        "완료"
//    }
//    println("결과: $result")
//}

suspend fun fetchFromSource(name: String, delayMS: Long): String {
    delay(delayMS)
    return "$name #1234"
}

fun main() = runBlocking {
    data class source(val name: String, val delayMS: Long) {
        override fun toString(): String {
            return "name=$name, delayMS=$delayMS"
        }
    }

    val sources = listOf(
        source("DB", 1000),
        source("API", 2000),
        source("Cache", 30),
    )
    val timeout: Long = 1500

    val start = System.currentTimeMillis()
    val result = sources.map { async { withTimeoutOrNull(timeout) { fetchFromSource(it.name, it.delayMS) } } }
        .awaitAll()

    println("result=$result, elapsed=${System.currentTimeMillis() - start}ms")
}
