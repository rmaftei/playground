package org.rmaftei.playkotlin

// https://play.kotlinlang.org/byExample/02_control_flow
fun main() {
    // when
    cases("Hello")
    cases(1)
    cases(0L)
    cases(MyClass())
    cases("hello")

    // Ranges
    for(i in 0..3) {             // 1
        print(i)
    }
    print(" ")

    for(i in 2..8 step 2) {      // 2
        print(i)
    }
    print(" ")

    for (c in 'a'..'d') {        // 3
        print(c)
    }
    print(" ")

    for (c in 'a'..'g' step 2) { // 4
        print(c)
    }
    print(" ")

    for (i in 3 downTo 0) {      // 5
        print(i)
    }
    print(" ")

    val x = 2
    if (x in 1..10) {            // 6
        print(x)
    }
    print(" ")

    val y = 3
    if (y !in 1..4) {            // 7
        print(y)
    }

    // Equality check
    val authors = setOf("Shakespeare", "Hemingway", "Twain")
    val writers = setOf("Twain", "Shakespeare", "Hemingway")

    println(authors == writers)   // 1
    println(authors === writers)  // 2
}

fun cases(obj: Any) {
    when (obj) {
        1 -> println("One")                          // 1
        "Hello" -> println("Greeting")               // 2
        is Long -> println("Long")                   // 3
        !is String -> println("Not a string")        // 4
        else -> println("Unknown")                   // 5
    }
}

class MyClass