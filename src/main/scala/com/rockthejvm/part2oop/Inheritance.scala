package com.rockthejvm.part2oop

object Inheritance {

  class Animal {
    val creatureType = "wild"
    def eat(): Unit = println("nomnomnnom")
  }

  class Cat extends Animal {
    def crunch() = {
      eat()
      println("crunch, crunch")
    }
  }

  val cat = new Cat

  class Person(val name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }

  class Adult(name: String, age: Int, idCard: String) extends Person(name) // must specify super-constructor

  // overriding
  class Dog extends Cat {
    override val creatureType: String = "domestic"
    override def eat(): Unit = println("mmm, I like this bone")

    // popular overridable method
    override def toString: String = "a dog"
  }

  // sybtype polymorphism
  val dog: Animal = new Dog
  dog.eat() // the most specific method will be ran

  // overloading vs overriding
  class Crocodile extends Animal {
    override val creatureType: String = "very wild"

    override def eat(): Unit = "I can eat anything, I'm a croc"

    // overloading = multiple methods with the same name, different signatures
    // different signature =
    //   different argument list(different number of arguments + different types of arguments)
    //   + different return type (optional)
    def eat(animal: Animal): Unit = println("I'm eating this poor fella")
    def eat(dog: Dog): Unit = println("I'm eating a dog")
    def eat(person: Person): Unit = println(s"I'm eating a human with the name ${person.name}")
    def eat(person: Person, dog: Dog) = println("I'm eating a human and a dog")
//    def eat(): Int = 45 // not valid overload because they have matching parameter types
    def eat(dog: Dog, person: Person) = println("I'm eating a human and a dog 2")

  }

  def main(args: Array[String]): Unit = {
    cat.eat()
    cat.crunch()

    println(dog) // println(dog.toString)

    val croc = new Crocodile
    val daniel = Person("Daniel", 99)
//
//    croc.eat(daniel, dog)
//    croc.eat(dog, daniel)
  }
}
