package ch05

class User(val name: String, val age: Int) {
    fun example() {
        val u = User("Kim", 30)
        println(u.name)
//        u.age=31
    }
}