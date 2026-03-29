package effectiveckotlin.ch1

fun <T: Comparable<T>> List<T>.quickSort(): List<T> {
    if (size < 2) return this
    val pivot = this.first()
    val (smaller, bigger) = drop(1)
        .partition { it < pivot }
    return smaller.quickSort() + pivot + bigger.quickSort()
}

fun main() {
//    println(listOf(3, 2, 5, 1, 6).quickSort())
//    println(listOf("C", "D", "A", "B").quickSort())

    val node = Node("parent")
    node.makeChild("child")
}

// 리시버가 불분명할 경우 리시버를 명시하거나, 피한다.
class Node(val name: String) {
    fun makeChild(childName: String) = create("$name.$childName")
//        .also { print("Created ${it?.name}") }
        .apply { print("Created ${this?.name} in ${this@Node.name}") } // 외부 리시버는 레이블을 사용한다.
    fun create(name: String): Node? = Node(name)
}

