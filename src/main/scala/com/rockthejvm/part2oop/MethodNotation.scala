package com.rockthejvm.part2oop

import scala.language.postfixOps

object MethodNotation {
  class Person(val name: String, val age: Int, favoriteMovie: String){
    infix def likes(movie: String): Boolean =
      movie == favoriteMovie

    def +(person: Person): String =
      s"${this.name} is hanging out with ${person.name}"

    def +(nickname: String): Person =
      new Person( s"$name $nickname", age, favoriteMovie)

    def unary_+ : Person = new Person(name, age+1, favoriteMovie)

    def !!(programmingLanguage: String): String = // ?, !. >>, <+> used in all sorts of frameworks cats, akka
      s"$name wonders how $programmingLanguage can be so cool"

    // prefix position
    // supported unary operators: -, +, ~, !
    def unary_- : String =
      s"$name's alter ego"

    def isAlive: Boolean = true

    def apply(): String =
      s"Hi, my name is $name and my favourite movie is $favoriteMovie"

    def apply(x: Int): String =
      s"Hi, my name is $name and my favourite movie is $favoriteMovie. Overloaded apply call at the power of $x"
  }

  val mary = new Person("Mary", 34, "Inception")
  val john = new Person("John", 36, "Fight Club")

  val negativeOne = -1

  /**
   * Exercises:
   * - a + operator on the Person class that returns a person with a nickname
   *   mary + "the rockstar" => new Person("Mary the rockstar, _, _)
   * - a UNARY operator that increases the person's age
   *   +mary =? new Person(_, _, age+1)
   * - an apply() method with an int arg
   *   -> already did that, see above
   * @param args
   */

  def main(args: Array[String]): Unit = {
    println(mary.likes("Fight Club"))
    // infix notation - for methods with ONE argument
    println(mary likes "Fight Club") // identical

    // "operator"
    println(mary + john)
    println(mary.+(john)) // identical

    println(2 + 3)
    println(2.+(3)) // same

    println(mary !! "Scala")

    // prefix position
    println(-mary)

    // postfix notation
    println(mary.isAlive)
    println(mary isAlive) // discouraged

    // apply is special
    println(mary.apply())
    println(mary()) // same
    println(mary(3)) // same

    val maryWithNickname = mary + "the sloppy"
    println(maryWithNickname.name)
    val olderMary = +mary
    println(olderMary.age)
  }
}
