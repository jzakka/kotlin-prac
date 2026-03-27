package effectiveckotlin.ch1

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

class Counter {
    private val lock = Any()
    private var num = 0

    fun inc() = synchronized(lock) {
        num += 1
    }

    fun dec() = synchronized(lock) {
        num -= 1
    }

    fun get(): Int = num
}

data class User2(val name: String)

class UserRepository {
    private var users: List<User2> = listOf()
    private var LOCK: Any = Any()

    fun loadAll(): List<User2> = users

    fun add(user: User2) = synchronized(LOCK) {
        users += user
    }
}

class UserRepositoryTest {
    fun `should add elements`() {
        val repo = UserRepository()
        val oldElements = repo.loadAll()
        repo.add(User2("B"))
        val newElements = repo.loadAll()
        assert(oldElements != newElements)
    }
}

fun main() {
    val repo = UserRepository()
    thread {
        for (i in 1..10000) {
            repo.add(User2("User $i"))
        }
    }
    thread {
        for (i in 1..10000) {
            val list = repo.loadAll()
            for (e in list) {

            }
        }
    }
}

