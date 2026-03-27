package effectiveckotlin.ch1

import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

val a = 1
fun fizz() {
    val b = 2
    print(a + b)
}
fun buzz() = runBlocking {
    val c = 3
    print(a + c)
}

data class User4(val name: String?)
fun main() {
    println(primes.take(10).toList())
}

fun updateWeather(degrees: Int) {
    val (description, color) = when {
        degrees < 5 -> "cold" to "blue"
        degrees < 23 -> "mild" to "yellow"
        else -> "cold" to "red"
    }
    // ...
}

// Seive of Eratosthenes
fun Eratosthenes() {
    var numbers = (2..100).toList()
    val primes = mutableListOf<Int>()
    while(numbers.isNotEmpty()) {
        val prime = numbers.first()
        primes.add(prime)
        numbers = numbers.filter { it % prime != 0 }
    }
    println(primes)
}

val primes: Sequence<Int> = sequence {
    var numbers = generateSequence(2) { it + 1 }

    while(true) {
        val prime = numbers.first()
        yield(prime)
        numbers = numbers.drop(1).filter { it % prime != 0 }
    }
}
