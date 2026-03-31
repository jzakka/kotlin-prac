package effectiveckotlin.ch1

import java.util.Date
import java.util.Locale.getDefault


class Item15 {
    var name: String? = null
        get() = field?.uppercase(getDefault())
        set(value) {
            if (!value.isNullOrBlank()) {
                field = value
            }
        }

    val fullName: String
        get() = "$name $surname"

    //    var date: Date = Date() // 기존
// 데이터를 더이상 Date객체로 저장하지 못하게 된 경우(직렬화 지원이 안된다던가)
    var millis: Long = 10000000 // 데이터를 millis등으로 저장
    var date: Date // date는 millis의 래퍼가 되고 기존 date에 대한 참조코드를 유지가능.
        get() = Date(millis)
        set(value) {
            millis = value.time
        }

    interface Person {
        val name: String // getName()이 존재
    }

    open class Supercomputer {
        open val theAnswer: Long = 42
    }

    class AppleComputer: Supercomputer() {
        override val theAnswer: Long = 1_800_275_2273
    }
}

