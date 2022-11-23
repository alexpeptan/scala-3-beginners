package com.rockthejvm.part2oop

object Generics {

  abstract class MyList[A] {
    def head: A
    def tail: MyList[A]
  }

  class Empty[A] extends MyList[A] {
    override def head: A = throw new NoSuchElementException
    override def tail: MyList[A] = throw new NoSuchElementException
  }

  class NonEmpty[A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  val listOfIntegers: MyList[Int] = new NonEmpty[Int](1, new NonEmpty[Int](2, new Empty[Int]))

  val firstNumber = listOfIntegers.head
  val adding = firstNumber + 3

  // multiple generic types
  trait MyMap[Key, Value]

  // generic methods
  object MyList {
    def from2Elements[A](elem1: A, elem2: A): MyList[A] =
      new NonEmpty[A](elem1, new NonEmpty[A](elem2, new Empty[A]))
  }

  val first2Numbers = MyList.from2Elements(1, 2)
  val first2Numbers_v2 = MyList.from2Elements(2, 3)
  val first2Numbers_v3 = new NonEmpty(1, new NonEmpty(2, new Empty))

  /**
   * Exercise: Genericize LList.
   * @param args
   */

  def main(args: Array[String]): Unit = {
    println(listOfIntegers)
  }
}
