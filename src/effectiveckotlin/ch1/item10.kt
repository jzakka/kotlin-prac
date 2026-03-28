package effectiveckotlin.ch1

import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.util.zip.ZipInputStream

data class Person10(val isAdult: Boolean, val name: String)

class View() {
    fun showPerson(person: Person10) {}
    fun showError() {}
    fun hideProgressWithSuccess() {}
    fun hidProgress() {}
}

fun A(view: View, person: Person10) {
    if (person != null && person.isAdult) {
        view.showPerson(person)
        view.hideProgressWithSuccess()
    } else {
        view.showError()
        view.hidProgress()
    }
}

fun B(view: View, person: Person10) {
    person?.takeIf { it.isAdult }
        ?.let {
            view.showPerson(it)
            view.hideProgressWithSuccess()
        } ?: run {
            view.showError()
            view.hidProgress()
    }
}

var person: Person10? = null
fun printName() {
    person?.let {
        println(it.name)
    }
}

data class Student(val name: String, val surname: String, val result: Int)

class SomeObject

fun example1(students: List<Student>) {
    students.filter {it.result >= 50}.joinToString (separator = "\n"){
        "${it.name} ${it.surname} ${it.result}"
    }.let (::println)

    var obj = FileInputStream("/file.gz")
        .let(::BufferedInputStream)
        .let(::ZipInputStream)
        .let(::ObjectInputStream)
        .readObject() as SomeObject
}
