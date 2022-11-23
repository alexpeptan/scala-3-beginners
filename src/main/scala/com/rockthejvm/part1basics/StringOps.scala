package com.rockthejvm.part1basics

object StringOps {

  val aString = "Hello, I am enjoying Scala"

  // string functions
  val secondChar = aString.charAt(1)
  val firstWord = aString.substring(0, 5) // "Hello"
  val words = aString.split(" ") // "Hello," "I" "am" "enjoying" "Scala"
  val startsWithHello = aString.startsWith("Hello") // true
  val allDashes = aString.replace(" ", "-")
  val allUpperCase = aString.toUpperCase() // Upper case
  val nChars = aString.length

  // other functions
  val reversed = aString.reverse
  val aBunchOfChars = aString.take(10)

  // parse to numeric
  val numberAsString = "2"
  val number = numberAsString.toInt

  // interpolation
  val name = "Alice"
  val age = 12
  val greeting = "Hello, I'm " + name + " and I am " + age + " years old."
  val greeting_v2 = s"Hello, I'm $name and I am $age years old."
  val greeting_v3 = s"Hello, I'm $name and I will be turning ${age+1} years old."

  // f-interpolation
  val speed = 1.2f
  val myth = f"$name can eat $speed%2.2f burgers per minute."

  // raw-interpolation
  val escapes = raw"This is a \n newline."

  def main(args: Array[String]): Unit = {
    println(secondChar)
    println(firstWord)
    println(words.toString())
    println(startsWithHello)
    println(allDashes)
    println(allUpperCase)
    println(nChars)
    println(reversed)
    println(aBunchOfChars)
    println(greeting)
    println(greeting_v2)
    println(greeting_v3)
    println(myth)
    println(escapes)
  }
}
