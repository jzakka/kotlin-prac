package ch16

data class User(val id: Long, val name: String, val email: String)

interface UserRepository {
    fun findBy(id: Long): User?
    fun addUser(user: User): User
    fun existsByEmail(email: String): Boolean
}

class UserService(private val repo: UserRepository) {
    fun getUser(id: Long): Result<User> {
        val user = repo.findBy(id) ?: return Result.failure(NoSuchElementException("No user with id $id"))
        return Result.success(user)
    }

    fun createUser(name: String, email: String): Result<User> {
        if (name.isBlank()) return Result.failure(IllegalArgumentException("Name is blank"))
        if (repo.existsByEmail(email)) return Result.failure(IllegalArgumentException("User with email $email already exists"))
        return Result.success(repo.addUser(User(0, name, email)))
    }
}
