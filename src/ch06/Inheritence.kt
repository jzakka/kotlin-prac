package ch06

class Inheritence {
    open class Animal(val name: String) {
        open fun move() {
            println("$name move")
        }
    }

    class Dog: Animal("Dog") {
        override fun move() {
            println("$name walk")
            super.move()
        }
    }
}