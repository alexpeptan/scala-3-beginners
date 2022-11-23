package com.rockthejvm.part2oop

import org.w3c.dom.css.Counter

import java.io.Writer

object OOBasics {

//  class Person(val name: String, age: Int) // class has constructor arguments

  class Person(val name: String = "Jane Doe", age: Int = 0) { // constructor signature
    // fields
    val allCaps = name.toUpperCase()
    // methods
    def greet(name: String): String = s"${this.name} sais: Hi, $name" // needs disambiguation

    // signature differs
    // OVERLOADING
    def greet(): String = s"Hi, everyone. My name is $name"

    // aux constructors
//    def this(name: String) = this(name, 0)
//    def this() = this("Jane Doe")
  }

  // class has constructor arguments

  val aPerson : Person = new Person("John", 26)

  val john = aPerson.name // class parameter != field
  // does not compile -> constructor value is not a field
  // -> later see case classes, or add val before constructor argument -> to generate a field with that name

  val johnYelling = aPerson.allCaps
  val johnSaysHiAlex = aPerson.greet("Alex")
  val johnSaysHi = aPerson.greet()

  val genericPerson = new Person()
  val genericPerson_2 = new Person() // we may learn this later in case classes

  def main(args: Array[String]): Unit = {
    println(aPerson) // toString apparently not implemented -> may need case class for that
    println(johnSaysHiAlex)
    println(johnSaysHi)

    println(genericPerson)
    println(genericPerson_2)

    val me = new Writer("Alex", "Peptan", 1985)
    println(me.fullName)

    val anotherWriter1 = new Writer("John", "Snow", 700)
    val anotherWriter2 = new Writer("Alex", "Peptan", 1985)

    val myNovel = new Novel("Memoires", 2038.toShort, me)
    println(myNovel.authorAge)
    println(myNovel.isWrittenBy(me))
    println(myNovel.isWrittenBy(anotherWriter1))
    println(myNovel.isWrittenBy(anotherWriter2))

    val anotherEdition = myNovel.copy(2060)
    println(anotherEdition.title)
    println(anotherEdition.author.fullName)
    println(anotherEdition.yearOfRelease)
    println(anotherEdition.authorAge)

    val counter = new Counter(10)
    val expected13 = counter.increment(3)
    expected13.print
    val expectedNegative = counter.increment(-70)
    expectedNegative.print
    val expectedVeryNegative = counter.decrement(200)
    expectedVeryNegative.print
  }

  /**
   * Exercise: imagine you/re creating a backend for a book publishing house
   * Create a novel and a writer class.
   *
   * Writer: first name, surname, year
   * - method: fullName
   *
   * Novel: name, year of release, author
   * - authorAge
   * - isWrittenBy(author)
   * - copy(new year of release) = new instance of novel
   * */

  class Writer(firstName: String, lastName: String, val yearOfBirth: Short) {
    def fullName: String = s"$firstName $lastName"
  }

  class Novel(val title: String, val yearOfRelease: Short, val author: Writer) {
    def authorAge: Short = (yearOfRelease - author.yearOfBirth).toShort

    def isWrittenBy(author: Writer) = this.author == author // or compare references? We'll see

    def copy(newYearOfRelease: Short) = Novel(this.title, newYearOfRelease, this.author)
  }

  /**
   * Exercise #2: An immutable counter class
   * - constructed with an initial value
   * - increment/decrement => new instance of counter - with new count
   * - increment(n)/decrement(n) => new instance of counter
   * - print()
   */

  class Counter(val counter: Int) {
    def increment(value: Int) = new Counter(counter + value)
    def decrement(value: Int) = new Counter(counter - value)
    def print = println(counter)
  }

}
