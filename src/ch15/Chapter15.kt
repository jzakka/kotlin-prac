package ch15

fun buildString2(action: StringBuilder.() -> Unit): String {
    val sb = StringBuilder()
    sb.action()
    return sb.toString()
}

//fun main() {
//    val result = buildString2 {
//        append("Hello, ")
//        append("Kotlin DSL!")
//    }
//    println(result)
//
//    val normalLambda: (StringBuilder) -> Unit = { it.append("normal")}
//    val receiverLambda: StringBuilder.() -> Unit = { append("receiver")}
//
//    val sb1 = StringBuilder()
//    normalLambda(sb1)
//    println(sb1)
//
//    val sb2 = StringBuilder()
//    sb2.receiverLambda()
//    println(sb2)
//
//}

data class ServerConfig(
    var host: String = "0.0.0.0",
    var port: Int = 80,
    var maxConnections: Int = 100,
    var enableTLS: Boolean = false,
)

fun serverConfig(init: ServerConfig.() -> Unit): ServerConfig {
    return ServerConfig().apply(init)
}

//fun main() {
//    val config1 = ServerConfig().apply {
//        host = "localhost"
//        port = 8080
//        enableTLS = true
//    }
//    println(config1)
//
//    val prod = serverConfig {
//        host = "api.example.com"
//        port = 443
//        enableTLS = true
//        maxConnections = 10000
//    }
//    println("prod: $prod")
//
//    val dev = serverConfig {
//        host = "localhost"
//        port = 3000
//    }
//    println("dev: $dev")
//}

@DslMarker
annotation class HtmlDsl

interface Element

@HtmlDsl
class Head : Element {
    var title: String = ""
    override fun toString(): String = "<head><title>$title</title></head>"
}

@HtmlDsl
class UL : Element {
    private val items = mutableListOf<String>()
    fun li(text: String) {
        items.add(text)
    }

    override fun toString(): String = "<ul>\n${items.joinToString("\n") { "      <li>$it</li>" }}\n    </ul>"
}

@HtmlDsl
class Body : Element {
    private val children = mutableListOf<Element>()
    private val texts = mutableListOf<String>()

    fun h1(text: String) {
        texts.add("<h1>$text</h1>")
    }

    fun p(text: String) {
        texts.add("<p>$text</p>")
    }

    fun ul(init: UL.() -> Unit) {
        children.add(UL().apply(init))
    }

    override fun toString(): String {
        val all = texts.map { "    $it" } + children.map { "    $it" }
        return "<body>\n${all.joinToString("\n")}\n  </body>"
    }
}

@HtmlDsl
class HTML {
    private val children = mutableListOf<Element>()

    fun head(init: Head.() -> Unit) {
        children.add(Head().apply(init))
    }

    fun body(init: Body.() -> Unit) {
        children.add(Body().apply(init))
    }

    override fun toString(): String = "<html>\n${children.joinToString("\n") { "  $it" }}\n</html>"
}

fun html(init: HTML.() -> Unit): HTML = HTML().apply(init)

//fun main() {
//    val page = html {
//        head {
//            title = "Kotlin DSL"
//        }
//        body {
//            h1("Hello DSL!")
//            p("Type-safe builder 로 생성한 HTML입니다.")
//            ul {
//                li("항목 1")
//                li("항목 2")
//                li("항목 3")
//            }
//        }
//    }
//    println(page)
//}

data class Route(val method: String, val path: String, val handler: String)

class RouterDsl {
    private val routes = mutableListOf<Route>()

    fun get(path: String, handler: String) {
        routes.add(Route("GET", path, handler))
    }

    fun post(path: String, handler: String) {
        routes.add(Route("POST", path, handler))
    }

    fun put(path: String, handler: String) {
        routes.add(Route("PUT", path, handler))
    }

    fun delete(path: String, handler: String) {
        routes.add(Route("DELETE", path, handler))
    }

    fun group(prefix: String, init: RouterDsl.() -> Unit) {
        val sub = RouterDsl()
        sub.init()
        sub.routes.forEach { route ->
            routes.add(route.copy(path = "$prefix${route.path}"))
        }
    }

    fun build(): List<Route> = routes.toList()
}

fun router(init: RouterDsl.() -> Unit): List<Route> {
    return RouterDsl().apply(init).build()
}

//fun main() {
//    val routes = router {
//        get("/health", "healthCheck")
//
//        group("/api/v1") {
//            get("/users", "listUsers")
//            post("/users", "createUser")
//
//            group("/admin") {
//                get("/stats", "getStats")
//                delete("/cache", "clearCache")
//            }
//        }
//
//        group("/api/v2") {
//            get("/users", "listUsersV2")
//        }
//    }
//
//    routes.forEach {
//        println("${it.method.padEnd(6)} ${it.path.padEnd(25)} -> ${it.handler}")
//    }
//}

class Assertion<T>(private val actual: T) {
    infix fun shouldBe(expected: T) {
        if (actual != expected) throw AssertionError("Expected <$expected> but was <$actual>")
        println("  PASS: $actual == $expected")
    }

    infix fun shouldNotBe(expected: T) {
        if (actual != expected) throw AssertionError("Expected NOT <$expected> but was <$actual>")
        println("  PASS: $actual != $expected")
    }
}

fun <T> T.should(): Assertion<T> = Assertion(this)

data class TestCase(val name: String, val action: () -> Unit)

class TestSuite(val name: String) {
    private val tests = mutableListOf<TestCase>()

    fun test(name: String, action: () -> Unit) {
        tests.add(TestCase(name, action))
    }

    fun run() {
        println("=== $name ===")
        tests.forEach { tc ->
            println("${tc.name}: ${tc.action()}")
            try {
                tc.action()
            } catch (e: AssertionError) {
                println("  FAIL - ${e.message}")
            }
        }
        println()
    }
}

fun describe(name: String, init: TestSuite.() -> Unit) {
    TestSuite(name).apply(init).run()
}

//fun main() {
//    describe("Calculator") {
//        test("addition") {
//            (2 + 3).should() shouldBe 5
//        }
//        test("subtraction") {
//            (10 - 4).should() shouldBe 6
//        }
//        test("string length") {
//            "hello".length.should() shouldBe 5
//        }
//        test("not equal") {
//            "kotlin".should() shouldNotBe "java"
//        }
//        test("failing test") {
//            (1 + 1).should() shouldBe 3
//        }
//    }
//}

fun query(init: QueryBuilder.() -> Unit): QueryBuilder {
    return QueryBuilder().apply(init)
}

class WhereBuilder {
    private val conditions = mutableListOf<String>()

    infix fun String.eq(value: Any) {
        val v =  if (value is String) "'$value'" else "$value"
        conditions.add("$this = $v")
    }

    infix fun String.gt(value: Any) {
        conditions.add("$this > $value")
    }

    infix fun String.inList(values: List<Any>) {
        conditions.add("$this IN (${values.map { if (it is String) {"\"$it\""} else {it} }
            .joinToString(",")})")
    }

    fun build(): String = conditions.joinToString(" AND ")
}

class JoinClause(private val table: String) {
    var onCondition: String = ""

    infix fun on(condition: String) {
        onCondition = condition
    }

    fun build(): String = "JOIN $table ON $onCondition"
}

class QueryBuilder {
    private var conlumns = listOf<String>()
    private var tableName = ""
    private var joins = mutableListOf<JoinClause>()
    private var whereClause = ""
    private var orderByClause = ""
    private var limitCount: Int? = null

    fun select(vararg cols: String) {
        conlumns = cols.toList()
    }

    fun from(table: String) {
        tableName = table
    }

    fun join(table: String): JoinClause{
        val jc = JoinClause(table)
        joins.add(jc)
        return jc
    }

    fun where(init: WhereBuilder.() -> Unit) {
        val where = WhereBuilder().apply(init).build()
        whereClause = where
    }

    fun orderBy(column: String, descending: Boolean = false) {
        orderByClause = "$column ${if (descending) "DESC" else "ASC"}"
    }

    fun limit(n: Int) {
        limitCount = n
    }

    override fun toString(): String {
        val parts = mutableListOf<String>()
        parts.add("SELECT ${conlumns.joinToString(", ")}")
        parts.add("FROM $tableName")
        if (joins.isNotEmpty()) parts.add(joins.joinToString(" ") { it.build() })
        if (whereClause.isNotEmpty()) parts.add("WHERE $whereClause")
        if (orderByClause.isNotEmpty()) parts.add("ORDER BY $orderByClause")
        if (limitCount != null) parts.add("LIMIT $limitCount")
        return parts.joinToString(" ")
    }

}

fun main() {
    // 1) 기본 SELECT
    val q1 = query {
        select("name", "age", "email")
        from("users")
    }
    println(q1)
    // SELECT name, age, email FROM users

    // 2) WHERE 조건
    val q2 = query {
        select("*")
        from("orders")
        where {
            "status" eq "completed"
            "amount" gt 1000
        }
    }
    println(q2)
    // SELECT * FROM orders WHERE status = 'completed' AND amount > 1000

    // 3) JOIN + ORDER BY + LIMIT
    val q3 = query {
        select("u.name", "o.amount", "o.created_at")
        from("users u")
        join("orders o") on "u.id = o.user_id"
        where {
            "o.status" eq "completed"
        }
        orderBy("o.created_at", descending = true)
        limit(10)
    }
    println(q3)
    // SELECT u.name, o.amount, o.created_at FROM users u JOIN orders o ON u.id = o.user_id WHERE o.status = 'completed' ORDER BY o.created_at DESC LIMIT 10

    // 4) IN 절
    val q4 = query {
        select("name", "email")
        from("users")
        where {
            "id" inList listOf(1, 2, 3)
        }
        orderBy("name")
    }
    println(q4)
    // SELECT name, email FROM users WHERE id IN (1, 2, 3) ORDER BY name ASC
}
