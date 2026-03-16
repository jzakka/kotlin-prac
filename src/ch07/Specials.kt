package ch07

class Specials {
    enum class Direction {
        NORTH, SOUTH, EAST, WEST
    }

    enum class Color(val rgb: Int) {
        RED(0xFF0000), GREEN(0x00FF00), BLUE(0x0000FF);

        fun hex() = "#${rgb.toString(16).padStart(6, '0')}"
    }

    sealed class Result {
        data class Success(val data: String) : Result()
        data class Failure(val message: String) : Result()
        data object Loading : Result()
    }

    fun handle(result: Result): String = when (result) {
        is Result.Success -> "데이터: ${result.data}"
        is Result.Failure -> "에러: ${result.message}"
        is Result.Loading -> "로딩 중..."
    }

    object AppConfig {
        val version = "1.0.0"
        fun info() = "App v$version"
    }

    @JvmInline
    value class Email(val value: String) {
        init {require(value.contains("@")) { "Email is not valid: $value" }}
    }

    @JvmInline
    value class UserId(val id: Long)

    fun sendEmail(to: Email) {}
}