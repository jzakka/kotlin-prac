package effectiveckotlin.ch1

open class Animal
class Bear: Animal()
class Camel: Animal()

interface Car

class Fiat126P: Car

val DEFAULT_CAR: Car = Fiat126P()

interface CarFactory {
    fun produce() = DEFAULT_CAR
}

fun main() {
    var animal = Bear()
//    animal = Camel()

}
