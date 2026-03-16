package ch06

interface Interfaces {
    fun doSomething()
    fun defaultMethod() = println("default method")
}

interface Interface2 {
    fun doSomething2()
}

class Structure: Interfaces, Interface2 {
    override fun doSomething() {
        println("$this is doing something")
    }

    override fun doSomething2() {
        println("$this is doing something 2")
    }

    fun method() {
        super.defaultMethod()
    }

}