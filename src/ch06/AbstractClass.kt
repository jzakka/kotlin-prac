package ch06

abstract class AbstractClass {
    abstract fun move()

    class DetailClass: AbstractClass() {
        override fun move() {
            println("$this")
        }
    }
}