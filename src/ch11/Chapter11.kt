package ch11

class Box<T>(val value: T)

val intBox = Box(42)
val strBox = Box("hello")

fun <T> firstOrNull(list: List<T>): T? {
    return if (list.isEmpty()) null else list[0]
}

fun <T: Comparable<T>> maxOf(a: T, b: T): T {
    return if (a > b) a else b
}

class Chapter11 {
    fun example() {
        maxOf(3, 7)
        maxOf("a", "z")

        isType<String>("hello")
//        isType<Int>("hello")
    }
}

open class Animal
class Cat: Animal()
class Dog: Animal()

interface Producer<out T> {
    fun produce(): T
}

val catProducer: Producer<Cat> = object : Producer<Cat> {
    override fun produce() = Cat()
}
val animalProducer: Producer<Animal> = catProducer

interface Consumer<in T> {
    fun consume(item: T)
}
val animalConsumer: Consumer<Animal> = object : Consumer<Animal> {
    override fun consume(item: Animal) {println(item)}
}
val catConsumer: Consumer<Cat> = animalConsumer

//fun <T> isType(value: Any): Boolean {
////    return value is T
//    return false
//}

inline fun <reified T> isType(value: T): Boolean {
    return value is T
}

interface Entity {
    val id: Int
}

data class User(override val id: Int, val name: String, val age: Int): Entity
data class Product(override val id: Int, val title: String, val price: Double): Entity

class Repository<T: Entity> {
    val storage: MutableMap<Int, T> = mutableMapOf()

    fun save(entity: T) {
        this.storage[entity.id] = entity
    }

    fun findById(id: Int): T? {
        return storage[id]
    }

    fun findAll(): List<T> {
        return storage.values.toList()
    }

    fun deleteById(id: Int): Boolean {
        return this.storage.remove(id) != null
    }

    fun filterBy(predicate: (T) -> Boolean): List<T> {
        return this.storage.values.filter { predicate(it) }
    }

    fun <R> mapTo(transform: (T) -> R): List<R> {
        return this.storage.values.map { transform(it) }
    }
}
