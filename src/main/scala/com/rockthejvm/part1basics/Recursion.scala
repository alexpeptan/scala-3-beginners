package com.rockthejvm.part1basics

import scala.annotation.tailrec

object Recursion {

  // "repetition" = recursion
  def sumUntil(n: Int): Int = if (n<=0) 0 else n + sumUntil(n-1)


  def sumUntil_v2(n: Int) : Int = {
    @tailrec
    def sumUntilTailrec(x: Int, accumulator: Int): Int =
      if (x>n) accumulator
      else sumUntilTailrec(x+1, x + accumulator)

    sumUntilTailrec(0, 0)
  }

  def sumNumbersBetween(a: Int, b: Int): Int =
    if (a>b) 0
    else a + sumNumbersBetween(a + 1, b)

  def sumNumbersBetween_v2(a: Int, b: Int): Int = {
    @tailrec
    def sumTailrec(currentNumber: Int, accumulator: Int): Int =
      if (currentNumber>b) accumulator
      else sumTailrec(currentNumber + 1, currentNumber + accumulator)

    sumTailrec(a, 0)
  }

  /**
   * Exercises:
   * 1. Concatenate a string n times
   * 2. Fibonacci function, tail recursive
   * 3. Is isPrime function tail recursive or not? - if not write a tail recursive function
   * @param args
   */

  def concatStringRepeteadly(s: String, n: Int): String = {
    @tailrec
    def concatTailrec(accumulator: String, timesLeft: Int): String =
      if (timesLeft <= 0) accumulator
      else concatTailrec(accumulator + s, timesLeft - 1)

    concatTailrec("", n)
  }

  def fibonacci(n: Int): Int = {
    @tailrec
    def fibTailrec(currentPos: Int, previous: Int, last: Int): Int =
      if (currentPos >= n) last
      else fibTailrec(currentPos + 1, last, previous + last)

    fibTailrec(1, 0, 1)
  }

  def isPrimeDanielOfficial(n: Int): Boolean = {
    @tailrec
    def isPrimeUntil(t: Int): Boolean =
      if (t <= 1) true
      else n % t != 0 && isPrimeUntil(t - 1)

    isPrimeUntil(n / 2)
  }

  def main(args: Array[String]): Unit = {
    println(sumUntil(10))
    println(sumUntil_v2(10))
    println(sumNumbersBetween(3, 10))
    println(sumNumbersBetween_v2(3, 10))
    println(concatStringRepeteadly("Ana ", 4))
    println(fibonacci(8))
    println(isPrimeDanielOfficial(99))
    println(isPrimeDanielOfficial(101))
  }


}
