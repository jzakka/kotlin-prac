package effectiveckotlin.ch1

import kotlinx.coroutines.runBlocking
import java.util.SortedSet
import java.util.TreeSet
import kotlin.concurrent.thread
import kotlin.jvm.Throws
import kotlin.properties.Delegates.observable

class BankAccount {
    var balance = 0.0
    private set

    fun deposit(depositAmount: Double) {
        balance += depositAmount
    }

    @Throws(InsufficientFunds::class)
    fun withdraw(withdrawAmount: Double){
        if(balance < withdrawAmount) {
            throw InsufficientFunds()
        }
        balance -= withdrawAmount
    }
}

class InsufficientFunds: Exception()

fun calculate(): Int {
    print("Calculating...")
    return 42
}

val fizz = calculate()
val buzz
    get() = calculate()

interface Element {
    val active: Boolean
}

class ActualElement: Element {
    override var active: Boolean = false
}

val name: String? = "Marton"
val surname: String = "Braun"

val fullName: String?
    get() = name?.let { "$it $surname" }

val fullName2: String? = name?.let { "$it $surname" }

data class FullName(var firstName: String, var lastName: String): Comparable<FullName> {
    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun compareTo(other: FullName): Int {
        return "$firstName$lastName".compareTo("${other.firstName}${other.lastName}")
    }

    override fun toString(): String {
        return "FullName(firstName='$firstName', lastName='$lastName', hashCode='${hashCode()}')"
    }
}

data class User (val name: String, val surname: String)

var names  by observable(listOf<String>()) { _, old, new ->
    println("Names changed from $old to $new")
}

fun main() = runBlocking {
    names = listOf("Chung")
    names += "Fabio"
}
