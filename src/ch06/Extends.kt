package ch06

class Extends {
    open class Animal(val name: String) {
        open fun sound(): String = "..."
    }

    class Dog(name: String) : Animal(name) {
        override fun sound(): String = "멍멍"
    }

    abstract class Shape {
        abstract fun area(): Double
        fun describe() = "넓이: ${area()}"
    }

    class Circle(val radius: Double): Shape() {
        override fun area(): Double = Math.PI * radius * radius
    }

    interface Printable {
        fun print()
        fun prettyPrint() = println(">>> $this")
    }

    class Document(val content: String) : Printable {
        override fun print() = println(content)
    }

    interface Flyable {
        fun fly(): String
    }

    interface Swimmable {
        fun swim(): String
    }

    class Duck: Flyable, Swimmable {
        override fun fly() = "Duck flying"
        override fun swim() = "Duck swim"
    }

    interface A {
        fun greet() = "Hello from A"
    }

    interface B {
        fun greet() = "Hello from B"
    }

    class C: A, B {
        override fun greet(): String {
            return "${super<A>.greet()} & ${super<B>.greet()}"
        }
    }
}