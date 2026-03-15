package ch05

class classes {
    class User(val name: String, var score: Int) {
        var gender: String = "Male"
        val intro: String
        get() = when {
            score >= 90 -> "$name is smart"
            else -> "$name is dumb"
        }

        companion object {
            fun WithGender(name:String, score: Int, gender: String): User {
                val u1 = User(name, 2)
                u1.gender = gender
                return u1
            }
        }
    }

    data class Grade(val name: String, val score: Int)

    fun example() {
        val u1 = User("John", 31)
//        u1.name = "James"
        u1.score = 444

        val g1 = Grade(u1.name, 31)
        val g2 = Grade(u1.name, 31)

        println(g1 == g2)

        println(u1.gender)

        val u2 = User.WithGender("James", 31, "Male")
        println(u2.intro)
        println(u2.gender)
    }
}