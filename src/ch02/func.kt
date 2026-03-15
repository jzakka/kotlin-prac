package ch02

class Fun {
    fun greet(name: String): String {
        return "Hello $name"
    }

    fun add(a: Int, b: Int) = a + b

    fun createUser(name: String, age: Int = 0, active: Boolean = true): String {
        return "$name (age=$age, active=$active)"
    }

    fun call() {
        createUser("Kim")
        createUser("Kim", 30)
        createUser("Kim", active = false)
        createUser(age = 25, name = "Lee")

        val (result, error) = divide(10.0, 3.0)
        val (name, age, active) = getUser()
    }

    fun divide(a: Double, b: Double): Pair<Double, String?> {
        if (b == 0.0) return Pair(.0, "division by zero")
        return Pair(a / b, null)
    }

    fun getUser(): Triple<String, Int, Boolean> = Triple("Kim", 30, true)

    fun printMessage(msg: String) {
        println(msg)
    }
}