package effectiveckotlin.ch1

import java.math.BigDecimal
import java.time.Duration
import java.time.ZonedDateTime

fun main() {
    val nextPrince = BigDecimal("10")
    val tax = BigDecimal("0.23")
    val currentBalance = BigDecimal("20")
    val newBalance = currentBalance - nextPrince * tax
    println(newBalance)

    val now = ZonedDateTime.now()
    val duration = Duration.ofDays(1)
    val sameTimeTomorrow = now + duration

    val num1 = BigDecimal("1.0")
    val num2 = BigDecimal("1.00")
    println(num1 == num2) // false
    println(num1 >= num2 && num1 <= num2) // true

    val SUPPORTED_TAGS = setOf("ADMIN", "TRAINER", "ATTENDEE")
    val tag = "ATTENDEE"

    println(SUPPORTED_TAGS.contains(tag))
    println(tag in SUPPORTED_TAGS)

    val ADMIN_TAG = "ADMIN"
//    val admins = users.map { user -> user.tags.contains(ADMIN_TAG)}
//    val admins = users.map { user -> ADMIN_TAG in user.tags}
}