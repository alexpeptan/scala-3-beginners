package com.rockthejvm.part3fp

object TuplesMaps {

  // tuples = finite ordered lists / group of values under the same "big value"
  val aTuple = (2, "rock the jvm") // Tuple2[Int, String] = (Int, String)
  val firstField = aTuple._1
  val aCopiedTuple = aTuple.copy(_1 = 54)

  // tuples of 2 elements
  val aTuple_v2 = 2 -> "rock the jvm" // IDENTICAL to (2, "rock the jvm")

  // maps: keys -> values
  val aMap = Map()
  val phoneBook: Map[String, Int] = Map(
    "Jim" -> 555,
    "Daniel" -> 789,
    "Jane" -> 123
  ).withDefaultValue(-1)

  // core APIs
  val phoneBookHasDaniel = phoneBook.contains("Daniel")
  val marysPhoneNumber = phoneBook("Mary") // crash with an exception

  // add a pair
  val newPair = ("Mary" -> 678)
  val newPhoneBook = phoneBook + newPair

  // remove a pair
  val phonebookWithoutDaniel = phoneBook - "Daniel"

  // list -> map
  val linearPhonebook = List(
    "Jim" -> 555,
    "Daniel" -> 789,
    "Jane" -> 123
  )
  val phonebook_v2 = linearPhonebook.toMap

  // map -> list
  val linearPhonebook_v2 = phoneBook.toList // toSeq, toArray, toVector, toSet

  // map, flatMap, filter
  // Map("Jim" -> 123, "jiM" -> 999) -> Map("JIM", ????) -> almost never an idea to change keys in a map
  val aProcessedPhonebook = phoneBook.map(pair => (pair._1.toUpperCase(), pair._2)) // danger of key collision in this case -> undetermined which one to keep in this case

  // filtering keys
  val noJs = phoneBook.view.filterKeys(!_.startsWith("J")).toMap

  // mapping values
  val prefixNumbers = phoneBook.view.mapValues(number => s"0255-$number").toMap

  // other collections can create maps
  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  val nameGroupings = names.groupBy(name => name.charAt(0))


  def main(args: Array[String]): Unit = {
    println(aCopiedTuple)

    println(phoneBook)
    println(phoneBookHasDaniel)
    println(marysPhoneNumber)

    println(newPhoneBook)
    println(phonebookWithoutDaniel)

    println(nameGroupings)
  }
}
