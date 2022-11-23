package com.rockthejvm.part2oop

object AnonymousClasses {
  abstract class Animal {
    def eat(): Unit
  }

  // classes used for just on instance are boilerplate-y
  class SomeAnimal extends Animal {
    override def eat(): Unit = println("I'm a weird animal.")
  }

  val someAnimal = new SomeAnimal
  val someAnimal_v2 = new Animal { // Abstract classes
    override def eat(): Unit = println("I'm a weird animal.")
  }

  /*
    class AnonymousClasses.AnonClass$1 extends Animal {
      override def eat(): Unit = println("I'm a weird animal.")
    }

    val someAnimal_v2 = new AnonymousClasses.AnonClass$1
  */

  class Person(name: String) {
    def sayHi() = println(s"Hi, my name is $name")
  }

  // works for classes (abstract or not) + traits
  val jim = new Person("Jim") {
    override def sayHi(): Unit = println("MY NAME IS JIM!")
  }

  def main(args: Array[String]): Unit = {

  }
}
