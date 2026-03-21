package ch13

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withTimeout

class Chapter13 {
    fun example() {}
}

//fun main() = runBlocking {
//    val ch = Channel<String>(10)
//    launch { ch.send("hello") }
//    val msg = ch.receive()
//
//    println(msg)
//}

//fun main() = runBlocking {
//    val ch = Channel<Int>(3)
//
//    launch {
//        for (i in 1..5) {
//            println("sending $i")
//            ch.send(i)
//        }
//        ch.close()
//    }
//
//    delay(500)
//    for (value in ch) {
//        println("received $value")
//    }
//}

//fun main() = runBlocking {
//    val numbers = produce {
//        for (i in 1..5) {
//            delay(100)
//            send(i)
//        }
//    }
//
//    for (n in numbers) {
//        println("got $n")
//    }
//}

//fun main() = runBlocking {
//    val jobs = Channel<Int>(10)
//    val results = Channel<String>(10)
//
//    launch {
//        for (i in 1..10) jobs.send(i)
//        jobs.close()
//    }
//
//    repeat(3) {workerId ->
//        launch {
//            for (job in jobs) {
//                delay(100)
//                results.send("Worker$workerId processed job$job")
//            }
//        }
//    }
//
//    launch {
//        delay(1500)
//        results.close()
//    }
//
//    for (result in results) {
//        println(result)
//    }
//}

fun numberFlow(): Flow<Int> = flow {
    for (i in 1..5) {
        delay(100)
        emit(i)
    }
}

//fun main() = runBlocking {
//    numberFlow()
//        .filter { it % 2 == 1 }
//        .map { it * it }
//        .collect { println(it) }
//}

//fun main() = runBlocking {
//    val nums = (1..10).asFlow()
//
//    println("=== take ===")
//    nums.take(3).collect { println("$it ") }
//    println()
//
//    println("=== reduce ===")
//    val sum = nums.reduce { acc, value -> acc + value}
//    println("sum = $sum")
//
//    println("=== toList ===")
//    val list = nums.filter { it > 7 }.toList()
//    println(list)
//
//    println("=== onEach ===")
//    nums.onEach { delay(50) }
//        .take(5)
//        .collect { println("$it ") }
//    println()
//}

fun riskyFlow(): Flow<Int> = flow {
    emit(1)
    emit(2)
    throw RuntimeException("뻥!")
    emit(3)
}

//fun main() = runBlocking {
//    riskyFlow()
//        .catch { e -> println("에러 잡음: ${e.message}") }
//        .collect { println(it) }
//}

//fun main() = runBlocking {
//    val ch1 = produce {
//        delay(300)
//        send("ch1 결과")
//    }
//    val ch2 = produce {
//        delay(100)
//        send("ch2 결과")
//    }
//
//    val winner = select<String> {
//        ch1.onReceive { it }
//        ch2.onReceive { it }
//    }
//    println("먼저 도착: $winner")
//
//    coroutineContext.cancelChildren()
//}

//fun main() = runBlocking {
//    val fast = produce {
//        repeat(3) {
//            delay(100)
//            send("fast-$it")
//        }
//    }
//
//    val slow = produce {
//        repeat(3) {
//            delay(250)
//            send("slow-$it")
//        }
//    }
//
//    repeat(6) {
//        val msg = select<String> {
//            fast.onReceiveCatching { it.getOrNull() ?: "fast closed" }
//            slow.onReceiveCatching { it.getOrNull() ?: "slow closed" }
//        }
//        println(msg)
//    }
//
//    coroutineContext.cancelChildren()
//}

//fun main() = runBlocking {
//    println("=== supervisorScope: 하나 실패해도 나머지 계속 ===")
//    supervisorScope {
//        launch {
//            delay(100)
//            throw RuntimeException("Task A 실패!")
//        }
//        launch {
//            delay(200)
//            println("Task B 정상 완료")
//        }
//        launch {
//            delay(300)
//            println("Task C 정상 완료")
//        }
//    }
//    println("supervisorScope 종료")
//}

//fun main() = runBlocking {
//    val handler = CoroutineExceptionHandler { _, e ->
//        println("잡힌 예외: ${e.message}")
//    }
//
//    val scope = CoroutineScope(SupervisorJob() + handler)
//
//    scope.launch {
//        delay(100)
//        throw RuntimeException("폭발!")
//    }
//    scope.launch {
//        delay(200)
//        println("나는 살아있다")
//    }
//
//    delay(500)
//    scope.cancel()
//}

data class Exchange(val name: String, val price: Int, val timestamp: Long)

fun CoroutineScope.nyse(): ReceiveChannel<Exchange> = produce {
    repeat(5) {
        delay(200)
        send(Exchange("NYSE", (100..110).random(), System.currentTimeMillis()))
    }
}

fun CoroutineScope.nasdaq(): ReceiveChannel<Exchange> = produce {
    repeat(5) {
        delay(300)
        send(Exchange("NASDAQ", (200..220).random(), System.currentTimeMillis()))
    }
}

fun CoroutineScope.krx(): ReceiveChannel<Exchange> = produce {
    repeat(5) {
        delay(150)
        send(Exchange("KRX", (50000..51000).random(), System.currentTimeMillis()))
    }
}

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { _, exception -> println("에러발생: ${exception.message}") }
    val scope = CoroutineScope(SupervisorJob() + handler)

    val channels = mutableMapOf(
        "NYSE" to scope.nyse(),
        "NASDAQ" to scope.nasdaq(),
        "KRX" to scope.krx(),
    )

    val exchanges = ArrayList<Exchange>()

    while (!channels.isEmpty()) {
        val (name, exch) = select {
            channels.forEach { (key, value) -> value.onReceiveCatching { key to it.getOrNull() } }
        }
        if (exch == null) {
            channels.remove(name)
        } else {
            println("[${exch.name}] price=${exch.price} at ${exch.timestamp}")
            exchanges.add(exch)
        }
    }

    val total = exchanges.size
    val grouped = exchanges.groupBy { it.name }
        .map { (name, exchanges) -> Pair(name, exchanges.map { it.price }.average()) }
        .toList()

    println("total=$total")
    grouped.forEach { (name, price) -> println("$name average: $price") }
    scope.cancel()
}


