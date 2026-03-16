package ch08

class NullSafety {
//    val name = getUser()?.name ?: "unknown"
//    val name: String = nullableValue!!

    val name: String = "ch08"

    class User(var name: String = "", var age: Int = 0)

    fun example() {
        val length = name?.let { it.length } ?: 0
        val user = User().apply {
            this.name = "Kim"
            this.age = 30
        }
    }
}