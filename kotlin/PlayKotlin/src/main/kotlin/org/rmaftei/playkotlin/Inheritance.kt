package org.rmaftei.playkotlin

// https://play.kotlinlang.org/byExample/01_introduction/07_Inheritance

fun main(args: Array<String>) {
    val dog = Yorkshire()

    println(dog.bark())

    val tiger = SiberianTiger()
    println(tiger.sayHello())

    val lion = Asiatic("Alex")
    println(lion.sayHello())

}

// in order to override it must by opened with 'open'
// same with the method
open class Dog {
    open fun bark() {
        println("wof wof")
    }
}

class Yorkshire: Dog() {
    override fun bark() {
        println("wif, wif")
    }
}

// Inheriting without default constructor

open class Tiger(private val origin: String) {
    fun sayHello() {
        println("A tiger from $origin says: grrhhh!")
    }
}

class SiberianTiger : Tiger("Siberia")

// Bypass constructor arguments
open class Lion(val name: String, val origin: String) {
    fun sayHello() {
        println("$name, the lion from $origin says: graoh!")
    }
}

class Asiatic(name: String) : Lion(name = name, origin = "India")





