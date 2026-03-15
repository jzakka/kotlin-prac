package ch05

class DataClass {
    class User(val name: String, val age: Int)
    data class DUser(val name: String, val age: Int)

    fun example() {
        val u1 = User("Kim", 30)
        val u2 = User("Kim", 30)
        println(u1 == u2)
        println(u1)

        val v1 = DUser("Kim", 30)
        val v2 = DUser("Kim", 30)
        println(v1 == v2)
        println(v1)

        val v3 = v1.copy(age = 31)
    }
}