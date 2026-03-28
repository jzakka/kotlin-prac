package effectiveckotlin.ch1

fun Int.factorial(): Int = (1..this).product()

fun Iterable<Int>.product(): Int = fold(1) { acc, i -> acc * i }

fun main() {
    val tripleHello = 3 timesRepeated { print("Hello")}
    tripledHello()
}

operator fun Int.times(operations: () -> Unit): () -> Unit = { repeat(this) { operations() } }

val tripledHello = 3 * { print("Hello") }

infix fun Int.timesRepeated(operation: () -> Unit) = {
    repeat(this) { operation() }
}
