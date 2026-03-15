package ch03

class ControlFlow {
    fun example() {
        val score = 0
        var result = if (score >= 90) "A" else "B"

        val grade = if (score >= 90) {
            println("Execellent!")
            "A"
        } else {
            "B"
        }

        result = when {
            score >= 90 -> "A"
            score >= 80 -> "B"
            score >= 70 -> "C"
            else -> "F"
        }

        for (i in 1..10) {}
        for (i in 0 until 10) {}
        for (i in 10 downTo 1) {}
        for (i in 0 until 10 step 2) {}

        var list: List<Int> = ArrayList()
        for (item in list) {}
        for ((i, item) in list.withIndex()) {}

        var count = 100
        while(count > 0) {
            count--
        }

        do {
            val input = readLine()
        } while (input != "quit")
    }
}