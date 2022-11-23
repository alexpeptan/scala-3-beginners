package com.rockthejvm.part2oop

object Objects {

  object MySingleton { // type + the only instance of this type
    val aField = 45
    def aMethod(x: Int) = x + 1
  }

  val theSingleton = MySingleton
  val anotherSingleton = MySingleton
  val isSameSingleton = theSingleton == anotherSingleton // true
  // objects can have fields and methods
  val theSingletonField = theSingleton.aField
  val theSingletonMethodCall = theSingleton.aMethod(99)

  class Person(name: String) {
    def sayHi() = s"Hi, my name is $name"
  }

  // companions = class + object with the same name in the same file
  object Person { // companion objects
    // can access each other's private fields and methods
    val N_EYES = 2
    def canFly(): Boolean = false
  }

  // methods and fields in classes are used for instance-dependent functionality
  val mary = new Person("Mary")
  val mary_v2 = new Person("Mary")
  val marysGreeting = mary.sayHi()

  // methods and fields in objects are used for instance-independent functionality - "static"
  val humansCanFly = Person.canFly()
  val noEyesHuman = Person.N_EYES

  // equality
  // 1. equality of reference
  val sameMary = mary.eq(mary_v2) // false, different instances
  val sameSingleton = theSingleton.eq(anotherSingleton) // true, same instance
  // 2. equaloity of "sameness" - in Java - defined as .equals
  val sameMary_v2 = mary equals mary_v2 // false -> we'll override this later
  val sameMary_v3 = mary == mary_v2 // same as equals, false

  // object can extend classes
  object BigFoot extends Person("BigFoot")

  // Scala application = object + def main(args: Array[String]): Unit
  /*
    In Java:
    public class Objects {
      public static void main(String[] args) { ... }
    }
  */
  // when we define an object we define a type and the only instance of that type
  // wherever I define main as below it's like I define a static method main in Java - 1:1
  def main(args: Array[String]): Unit = {
    println(isSameSingleton)
    println(sameMary)
    println(sameMary_v2)
    println(sameSingleton)
    println(sameMary_v3)
  }
}
