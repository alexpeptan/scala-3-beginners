package com.rockthejvm.part3fp

import scala.annotation.tailrec

object HOFsCurrying {

  // Higher order functions (HOFs)
  val aHof: (Int, (Int => Int)) => Int = (x, func) => x + func(x) + 1
  val anotherHof: Int => (Int => Int) = x => (y => y + 2 * x)

  // quick exercise
//  val superfunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) =
  val supersubf1: (Int => Boolean) => Int = func1 => if func1(1) then 14 else 13
  val supersubf2: (String, (Int => Boolean)) => Int = (str: String, func2) => (str.length + (if func2(1) then 14 else 13))
  val supersubf2_v2: (String, (Int => Boolean)) => Int = (str: String, func2) => (str.length + supersubf1(func2)) // same
  val superfunction1: (Int, (String, (Int => Boolean)) => Int) => Int =
    (x: Int, higherFunc1) => (x + higherFunc1("Ana", _ % 3 == 0))
  val superfunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) =
    (x: Int, higherFunc1) => y => x + y + higherFunc1("Ana", _ % 3 == 0)

  // examples of HOFs: map, flatMap, filter

  // more examples
  // f(f(f(...(f(x)))

  @tailrec
  def nTimes(f: Int => Int, n: Int, x: Int): Int = {
    if (n <= 0) x
    else nTimes(f, n-1, f(x))
  }

  def plusOne(x: Int) = x + 1

  val tenThousandTimes = nTimes(plusOne, 10000, 0)

  // because we are returning another function we will NEVER be able to bake this function tail recursive
  def nTimes_v2(f: Int => Int, n: Int): Int => Int = {
    if (n <= 0) identity[Int] // (x: Int) => x
//    else (x: Int) => nTimes_v2(f, n - 1)(f(x))
    else (x: Int) => f(nTimes_v2(f, n - 1)(x))
  }
  val oneHundredTimes_v2 = nTimes_v2(_ + 1, 100)

  // currying = HOFs returning function instances
  val superadder: Int => Int => Int = (x: Int) => (y: Int) => x + y
  val add3: Int => Int = superadder(3)
  val invokeSuperadder: Int = superadder(3)(100)  // 103

  // curried methods = methods with multiple arg lists
  def curriedFormatter(fmt: String)(x: Double): String = fmt.format(x)

  val standardFormat: (Double => String) = curriedFormatter("%4.2f") // (x: Double) => "%4.2f".format(x)
  val preciseFormat: (Double => String) = curriedFormatter("%10.8f") // (x: Double) => "%10.8f".format(x)

  /**
   * 1. LList exercises:
   *  - foreach(A => Unit): Unit
   *    [1,2,3].foreach(x => println(x))
   *
   *  - sort((A, A) => Int): LList[A]
   *    [3,4,1,2].sort((x, y) => x - y) = [1,2,3,4]
   *
   *  - zipWith[B](LList[A], (A, A) => B): LList[B]
   *    [1,2,3].zipWith([4,5,6], (x, y) => x * y) = [1 * 4, 2 * 5, 3 * 6] = [4,10,18]
   *
   *  - foldLeft[B](start: B)((A, B) => B): B
   *    [1,2,3,4].foldLeft[Int](0)(x + y) = 10
   *    0 + 1 = 1
   *    1 + 2 = 3
   *    3 + 3 = 6
   *    6 + 4 = 10
   *
   * 2. toCurry(f: (Int, Int) => Int): Int => Int => Int
   *    fromCurry(f: (Int => Int => Int)): (Int, Int) => Int
   *
   * 3. compose(f,g) => x => f(g(x))
   *    andThen(f,g) => x => f(f(x))
   */

  def toCurry(f: (Int, Int) => Int): Int => Int => Int =
    (x: Int) => (y: Int) => f(x, y)
  def fromCurry(f: (Int => Int => Int)): (Int, Int) => Int =
    (x: Int, y: Int) => f(x)(y)

  def compose[A, B, C](f: B => C, g: A => B): A => C = (x: A) => f(g(x))
  def andThen[A, B, C](f: A => B, g: B => C): A => C = (x: A) => g(f(x))

  val square = (x: Int) => x * x
  val double: Int => Int = 2 * _
  val doubleSquare = compose(double, square)
  val doubleSquare3 = doubleSquare(3)

  val squareDouble = andThen[Int, Int, Int](double, square)
  val squareDouble3 = squareDouble(3)

  val adderCurry = toCurry(_ + _)
  val plus1 = adderCurry(1)

  val curryMultiplication = (x: Int) => (y: Int) => x * y
  val uncurriedMultiplication = fromCurry(curryMultiplication)
  val multiplicationUsage = uncurriedMultiplication(4, 5)

  def main(args: Array[String]): Unit = {
    println(supersubf1(_ % 2 == 0)) // 13
    println(s"${supersubf1(_ % 2 == 1)}\n") // 14

    println(supersubf2("Ana", _ % 2 == 0)) // 3 + 13 = 16
    println(s"${supersubf2("Ana", _ % 2 == 1)}\n") // 3 + 14 = 17
    println(supersubf2_v2("Ana", _ % 2 == 0)) // 3 + 13 = 16 // same
    println(s"${supersubf2_v2("Ana", _ % 2 == 1)}\n") // 3 + 14 = 17 // same

    println(superfunction1(33, (str: String, func2) => (str.length + (if func2(1) then 14 else 13)))) // 33 + 13 = 49
    println(s"${superfunction1(33, supersubf2)}")  // same -

    println(superfunction(33, supersubf2))
    println(superfunction(33, supersubf2)(-1000))
    println(superfunction(33, supersubf2_v2)(-1000))

    println(tenThousandTimes)
    println(oneHundredTimes_v2(100))
    println(add3(23))
    println(invokeSuperadder)

    println(standardFormat(Math.PI))
    println(preciseFormat(Math.PI))

    println(plus1(4))
    println(multiplicationUsage)

    println(doubleSquare3)
    println(squareDouble3)
  }
}
