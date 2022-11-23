package com.rockthejvm.part2oop

object AbstractDataType {

  abstract class Animal {
    val creatureType: String // abstract
    def eat(): Unit
    // non-abstract fields/methods allowed
    def  preferredMeal: String = "anything"
  }

  // abstract class cannot be instantiated
//  val anAnimal : Animal = new Animal()

  // non-abstract classes must implement the abstract methods/fields
  class Dog extends Animal {
    override val creatureType: String = "domestic"
    override def eat(): Unit = println("Crunching this bone")
    // overriding is legal for everything
    override val preferredMeal: String = super.preferredMeal // overriding accessor method with a field
  }

  // traits
  trait Carnivore { // Scala 3 - traits can have constructor args
    def eat(animal: Animal): Unit
  }

  class TRex extends Carnivore {
    override def eat(animal: Animal): Unit = println("I'm a T-Rex. I eat animals")
  }

  // practical
  // one class inheritance
  // multiple traits inheritance - mixing
  trait ColdBlooded
  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "croc"
    override def eat(): Unit = println("I'm a croc. I just eat stuff")
    override def eat(animal: Animal): Unit = println("croc eating animal")
  }

  /**
   * Philosophical difference between abstract classes vs traits
   * - abstract classes are THINGS
   * - traits are BEHAVIORS
   */

  /*
    Any
      AnyRef
        All classes we write
          scala.Null (the null reference)
      AnyVal
        Int, Boolean, Char, ...


          scala.Nothing

  */

  val aNonExistentAnimal: Animal = null
  val anInt: Int = throw new NullPointerException

  val aDog: Animal = new Dog

  def main(args: Array[String]): Unit = {

  }
}
