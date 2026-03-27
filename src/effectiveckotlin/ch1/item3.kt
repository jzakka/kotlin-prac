package effectiveckotlin.ch1

val repo = UserRepo()
val user1 = repo.user
val user2: User = repo.user
val user3: User? = repo.user

fun statedType() {
    val value: String = JavaClass().value
    println(value.length)
}

// platform type: Nullable하지만 문법적으로는 Null 아닌 것처럼 동작. 없애야 함.
// ㄴ 다른 언어에서 왔으며 널 가능성을 알 수 없는 타입
fun platformType() {
    val value = JavaClass().value
    println(value.length)
}

fun main() {
    val repo: TerribleUserRepo = RepoImpl()
    val text: String = repo.getUserName()
    print("User name length is ${text.length}")

}

interface TerribleUserRepo {
    fun getUserName() = JavaClass().value
}

class RepoImpl: TerribleUserRepo {
    override fun getUserName(): String? {
        return null
    }
}
