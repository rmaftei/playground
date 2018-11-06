package org.rmaftei.playkotlin

// https://play.kotlinlang.org/byExample/01_introduction/02_Functions

fun main() {
    printMessage("Salut")
    printMessageWithPrefix("Ce faci?", "")
    printMessageWithPrefix("Fara prefix")

    println("da " de 3)
    println("1" cu "a")

    val maria = Person("Maria")
    val ion = Person("Ion")

    maria likes ion

    maria.likedPeople.forEach { x -> println(x.name) }

    println(3 * "nu ")

    val str = "Always forgive your enemies; nothing annoys them so much."
    println(str[0..14])

    printAllWithPrefix(
        "Hello", "Hallo", "Salut", "Hola", "你好",
        prefix = "Greeting: "                                          // 4
    )
}

fun printMessage(message: String): Unit {
    println(message)
}

// default parameter
fun printMessageWithPrefix(message: String, prefix: String = "Info"): Unit {
    println("[$prefix] $message")
}

// infix functions

infix fun String.de(count:Int) = this.repeat(count)
infix fun String.cu(other: String) = Pair(this, other)

class Person(val name: String) {

    val likedPeople = mutableListOf<Person>()

    infix fun likes(other: Person) { likedPeople.add(other) }
}

// Operator functions
operator fun Int.times(str: String) = str.repeat(this)

operator fun String.get(range: IntRange) = substring(range)

// Functions with vararg parameters

fun printAllWithPrefix(vararg messages: String, prefix: String) {  // 3
    for (m in messages) println(prefix + m)
}

