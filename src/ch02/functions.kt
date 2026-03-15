package ch02

class functions {
    fun add(a: Int, b: Int): Int {
        return a + b
    }

    fun sub(a: Int, b: Int) = a - b

    fun mul(a: Int, b: Int = 1) = a * b

    fun div(a: Double, b: Double): Pair<Double, String?> {
        if (b == 0.0) {
            return Pair(a, "division by zero")
        }

        return Pair(a / b, null)
    }

    fun call() {
        val (res, err) = div(1.0, 0.0)
    }
}