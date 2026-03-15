package ch03

class Example{
    fun example() {
        val number = 1
        val numStr = if (number == 1) "one" else "not one"

        val score = 100
        val grade = when {
            score >= 90 -> "A"
            score >= 80 -> "B"
            score >= 70 -> "C"
            else -> "F"
        }

        val numbers: List<Int> = ArrayList()
        for (i in numbers) {
            println(i)
        }

        for ((i, e) in numbers.withIndex()) {
            println("$i -> $e")
        }
    }
}