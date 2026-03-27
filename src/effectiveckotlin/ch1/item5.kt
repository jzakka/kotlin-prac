package effectiveckotlin.ch1

import kotlin.contracts.contract

fun factorial(n: Int): Long {
    require(n >= 0) {
        "Cannot calculate factorial of $n " +
                "because it is smaller than 0"
    }
    return if (n <= 1) 1 else factorial(n - 1) * n
}

data class User5(val name: String, val email: String) {
    init {
        require(name.isNotEmpty())
//        require(isValidEmail(email))
    }
}

fun speak(text: String) {
//    check(isInitialized)
}

fun getUserInfo(email: String) {
//    checkNotNull(token)
}

class Dress
data class Person(val outfit: Any, val email: String?)

fun changeDress(person: Person) {
    require(person.outfit is Dress)
    val dress: Dress = person.outfit
}

fun sendEmail(person: Person, message: String) {
    require(person.email != null)
//    requireNotNull(person.email)
    val email: String = person.email
}

fun largestOf(a: Int, b: Int, c: Int, d: Int): Int = listOf(a, b, c, d).maxOrNull()!! // NPE 위험 존재

fun sendEmail2(person: Person, text: String) {
//    val email: String = person.email ?: return
    val email: String = person.email ?: run {
        print("Email not sent, no email address")
        return
    }
}

sealed class Message {
    data class TextMessage(val text: String): Message()
    data class ImageMessage(val image: Any): Message()
}

fun handleMessage(message: Message) = when(message) {
    is Message.TextMessage ->  showText(message.text)
    is Message.ImageMessage -> showImage(message.image)
    else -> error("Unknown message type")
}

fun showImage(image: Any) {
    TODO("Not yet implemented")
}

fun showText(message: String) {
    println(message)
}
