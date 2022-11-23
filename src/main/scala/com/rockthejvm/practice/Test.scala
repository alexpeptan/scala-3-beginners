package com.rockthejvm.practice

object Test {
  def main(args: Array[String]): Unit = {
    val myPredicate = new EvenPredicateX
    println(myPredicate.test(3))
    println(myPredicate.test(4))
  }
}

trait PredicateX[T] {
  def test(element: T): Boolean
}

class EvenPredicateX extends PredicateX[Int] {
  override def test(element: Int) =
    element % 2 == 0
}
