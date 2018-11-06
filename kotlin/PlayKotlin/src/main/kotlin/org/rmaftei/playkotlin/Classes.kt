package org.rmaftei.playkotlin

// https://play.kotlinlang.org/byExample/01_introduction/05_Classes
fun main(args: Array<String>) {
    val customer = Customer()

    val contact = Contact(1, "email@email.ro")

    println(contact.email)
    contact.email = "changed@email.ro"
    println(contact.email)

    rentPrice(10,2,1)
    rentPrice(10,2,1)
}

class Customer

class Contact(val id: Int, var email: String)

// Object keyword
fun rentPrice(standardDays: Int, festivityDays: Int, specialDays: Int): Unit {  //1

    val dayRates = object {                                                     //2
        var standard: Int = 30 * standardDays
        var festivity: Int = 50 * festivityDays
        var special: Int = 100 * specialDays
    }

    val total = dayRates.standard + dayRates.festivity + dayRates.special       //3

    print("Total price: $$total")                                               //4

}