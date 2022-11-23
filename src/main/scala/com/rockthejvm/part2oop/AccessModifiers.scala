package com.rockthejvm.part2oop

object AccessModifiers {

  class Person(val name: String) {
    protected def sayHi(): String = s"Hi, my name is $name"
    private def watchNetflix(): String = "Watching my favorite series..."
  }

  // protected == access to inside the class + children classes
  class Kid(override val name: String, age: Int) extends Person(name) {
    def greetPolitely(): String = {
      sayHi() + " I love to play!"
    }
  }

  val aPerson = new Person("Alice ")
  val aKid = new Kid("David", 5)

  // complication
  class KidWithParents(override val name: String, age: Int, momName: String, dadName: String) extends Person(name) {
    val mom = new Person(momName)
    val dad = new Person(dadName)

//    def everyoneSayHi(): String = this.sayHi() + s"Hi, I'm $name, and here are my parents: "
//      + mom.sayHi() + dad.sayHi() // not legal
  }

  def main(args: Array[String]): Unit = {
//    println(aPerson.sayHi())
    println(aKid.greetPolitely())
  }
}
