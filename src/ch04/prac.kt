package ch04

class prac {
    fun example() {
//        val nums = listOf(1, 2, 3)
        val mutableNums = mutableListOf(1, 2, 3)
        mutableNums.add(4)

        val fruits = setOf("apple", "banana", "apple")
        val mutableFruits = mutableSetOf("apple")
        mutableFruits.add("banana")

        val m = mapOf("a" to 1, "b" to 2)
        val mutableM = mutableMapOf("a" to 1)

        val v = m["c"]
        val v2 = m.getOrDefault("c", 0)

        val nums = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        nums.filter { it % 2 == 0 }
        nums.map { it * 2 }
        nums.filter { it % 2 == 0 }
            .map { it * it }

        nums.reduce { acc, n -> acc + n }
        nums.fold(100) { acc, n -> acc + n }

        val words = listOf("apple", "banana", "avocado", "blueberry")
        words.groupBy { it.first() }

        nums.first { it > 5 }
        nums.find { it > 100 }

        nums.filter { it > 3 }.map { it * 2 }

        nums.asSequence()
            .filter { it > 3 }
            .map { it * 2 }
            .toList()
    }
}