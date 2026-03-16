package ch08

object ProfileService {
    val users: MutableMap<Int, User> = mutableMapOf(
        1 to User("James", "james@gmail.com", Address("Seoul")),
        2 to User("John", "john@gmail.com", Address("Seoul", 23132)),
        3 to User("Hyle", "hyle@gmail.com", Address("LA", 12221))
    )

    fun findUser(id: Int): User? {
        return this.users[id]
    }

    fun getUserCity(id: Int): String {
        return this.users[id]?.address?.city ?: "주소 미등록"
    }

    fun printUserInfo(id: Int) {
        val userInfo = this.users[id]?.let {it}?.run { "name: ${this.name}, email: ${this.email}" } ?: "no user info of $id"
        println(userInfo)
    }

    fun createDefaultUser(name: String): User {
        return User(name=name).apply {
            val nextKey = users.keys.max() + 1
            users[nextKey] = this
        }
    }
}

class User(val name: String, val email: String? = null, val address: Address? = null)

class Address(val city: String, val zipCode: Int? = null)