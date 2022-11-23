package com.rockthejvm.part2oop

object CaseClasses {

  // lightweight data structures
  case class Person(name: String, age: Int)

  // 1 - class args are now fields -> no val needed for that -> compiler does this for us
  val daniel = new Person("Daniel", 99)
//  val danielAge = daniel.age

  // 2 - toString, equals, hashCode
  val danielToString = daniel.toString
  val danielDuped = new Person("Daniel", 99)
//  val isSameDaniel = daniel.equals(danielDuped) // true
  val isSameDaniel = daniel == danielDuped // true - same

  // 3 - utility methods
  val danielYounger = daniel.copy(age=78) // new Person with "Daniel" and age 78

  // 4 - CCs have companion objects
  val thePersonSingleton = Person
  val daniel_v2 = Person.apply("Daniel", 99) // "constructor"

  // 5 - CCs are serializable - can be turned from the in memory representation(binary) into a seroes of
  // bytes or characters that some other computer or some other JVM or some human can understand
  // Serialization for the JVM specifically = you can send it over the wire to another JVM (from the same PC or another)
  // Extremely useful in multi-threaded or distributed apps -> sent this data over the wire and the receiving JVM
  // will handle them automagically
  //
  // Akka - actor framework - good use case for case classes
  // Akka actors will need to obtain serialization for the messages that you send/receive in some actor
  // be it on the same JVM or some other machine

  // 6 - CCs have extractor pattern for PATTERN-MATCHING

  // can't create CCs with no arg lists
//  case class CCwithNoArgs {
//    // some code
//  }
//
//  val ccna = CCwithNoArgs
//  val ccna_v2 = new CCwithNoArgs // all instances would be equal -> because equal implemented based on equality of data
//  // and no data that can individualize the instances can be passed to CCwithNoArgs -> that's why this is illegal

  case object UnitedKingdom {
    // fields and methods
    def name: String = "The UK of GB and NI"
  }

  case class CCWithArgListNoArgs[A]() // legal, mainly used in the context of generics



  def main(args: Array[String]): Unit = {
    println(daniel)
    println(danielToString)
    println(isSameDaniel)
    println(danielYounger)
  }
}
