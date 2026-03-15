package ch01

class Main {
    private fun main() {
        val age = 10

        val name: String = "kotliner"

        var count = 0

        count = 1

        val fixed = 0
//        fixed = 1

    }

    fun Nullabel() {
        var name: String? = null
        var age: Int = 0

        println(name?.length)
        println(name?.length ?: 0)
    }

    fun StringTemplate(){
        val name = "kotliner"
        "Hello, $name! age: ${10 + 20}"
    }
}
