package ch04

class collections {
    fun Main () {
        val imList = listOf(1, 2, 3)
        val mList = mutableListOf(1, 2, 3)
//        imList.add(1)
        mList.add(1)

        val imMap = mapOf(1 to "one", 2 to "two", 3 to "three")
        println(imMap[1])
        println(imMap.getOrDefault(4, "four"))
        println(imMap[4])

        println(imList.filter { it > 2 }.map { it * 2 }.groupBy { it % 2 })
        println(imList.asSequence().filter { it > 2 }.map { it * 2 }.toList())
    }
}