import ch09.buildHtml
import ch09.maskEmail
import ch09.secondMax
import ch09.toSlug

fun main() {
// String 확장
    println("Hello World Kotlin".toSlug())  // "hello-world-kotlin"
    println("user@gmail.com".maskEmail())   // "u***@gmail.com"

    // List 확장
    println(listOf(3, 1, 4, 1, 5, 9).secondMax())  // 5

    // HTML 빌더
    val html = buildHtml {
        head("My Page")
        body("Hello, World!")
    }
    println(html)
    // <html><head><title>My Page</title></head><body>Hello, World!</body></html>

}