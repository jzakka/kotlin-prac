package ch01

fun main() {
    val a = 10
    var b = 10

    b = 20

    val nullable: String? = null

    println(nullable?.length ?: "empty")

    println("Hi! Kotlin? I'm $a years old")
}