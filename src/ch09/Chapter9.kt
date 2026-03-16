package ch09

import java.util.Locale.getDefault

class Chapter9 {
    fun example() {
        "Hello".addExclamation()

        "Kotlin".firstChar
        "Kotlin".lastChar
    }
}

fun String.addExclamation(): String {
    return this + "!"
}

val String.firstChar: Char
get() = this[0]

val String.lastChar: Char
get() = this[this.length - 1]

fun buildString(action: StringBuilder.() -> Unit): String {
    val sb = StringBuilder()
    sb.action()
    return sb.toString()
}

val result = buildString {
    append("Hello")
    append(", ")
    append("World")
}

open class Animal
class Dog: Animal()

fun Animal.sound() = "..."
fun Dog.sound() = "Woof!"

fun printSound(animal: Animal) {
    println(animal.sound())
}

fun String?.orEmpty(): String {
    return this ?: ""
}

fun String.toSlug(): String {
    return replace(' ', '-').lowercase(getDefault())
}

fun String.maskEmail(): String {
    val (prefix, suffix) = this.split('@')
    val newPrefix = prefix[0] + "*".repeat(prefix.length -1)
    return "$newPrefix@$suffix"
}

fun List<Int>.secondMax(): Int? {
    val sorted = this.sorted().reversed()
    if (sorted.size < 2) {
        return null
    }
    return sorted[1]
}

fun buildHtml(action: HtmlBuilder.() -> Unit): String {
    val builder = HtmlBuilder()
    builder.action()
    return builder.build()
}

class HtmlBuilder {
    private var headContent = ""
    private var bodyContent = ""

    fun head(title: String) {
        headContent = title
    }

    fun body(body: String) {
        bodyContent = body
    }

    fun build(): String {
        return "<html><head><title>$headContent</title></head><body>$bodyContent</body></html>"
    }
}
