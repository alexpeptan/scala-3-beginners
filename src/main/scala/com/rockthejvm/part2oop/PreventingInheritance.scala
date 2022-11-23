package com.rockthejvm.part2oop

object PreventingInheritance {

  class Person(name: String) {
    final def enjoyLife(): Int = 42
  }

  class Adult(name: String) extends Person(name) {
//    override def enjoyLife(): Int = 999 // cannot override final method
  }

  final class Animal

//  class Cat extends Animal // illegal

  // sealing a type hierarchy = inheritance only permitted inside this file
  sealed class Guitar(nStrings: Int)
  class ElectricGuitar(nStrings: Int) extends Guitar(nStrings)
  class AcusticGuitar extends Guitar(6)

  // no modifier = "not encouraging" inheritence
  // not mandatory, good practice
  open class ExtensibleGuitar(nStrings: Int) // open == specifically marked for extensionn

  def main(args: Array[String]): Unit = {

  }
}
