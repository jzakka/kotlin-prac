package ch05

class SecondaryConst {
    class User(val name: String, val age: Int) {
        constructor(name: String): this(name, 0)

        companion object {
            fun create(name: String) = User(name, 0)
        }
    }

    fun example() {
        val u = User("Kim")
    }
}