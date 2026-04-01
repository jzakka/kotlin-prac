package effectiveckotlin.ch1

interface Tree
class Birch : Tree
class Spruce : Tree

class Forest<T : Tree> {

    //　여기서의 T는 class 선언의 타입 매개변수 T와 다름 (섀도잉)
//    fun <T: Tree> addTree(t: T) {}

    //　권장
    fun addTree(t: T) {}

    // 새로운 타입 매개변수가 필요한 경우
    fun <ST: Tree> addTree(t: ST) {}
}


// 공변성
class Producer<out T> (private val item: T){
    fun produce(): T = item
}

val stringProd: Producer<String> = Producer("Hi")
val anyProd: Producer<Any> = stringProd // T는 함수 반환값으로만 사용됨 -> anyProd의 반환값은 any -> 당연히 string도 반환 가능

// 반공변성
class Consumer<in T> () {
    fun consume(t: T) {}
}

val anyConsumer: Consumer<Any> = Consumer()
val stringConsumer: Consumer<String> = anyConsumer // T는 함수 인자로만 사용됨 -> stringConsume인자는 string -> 실제 객체는 anyConsumer이고  consume(any)에 string이 들어가니 당연히 처리 가능.