package com.rockthejvm.part1basics

object Functions {

  // function = reusable piece of code that you can invoke with some arguments and return a result
  def aFunction(a: String, b: Int): String = {
    a + " " + b
  }

  val aFunctionInvocation = aFunction("Scala", 9999)

  def aNoArgFunction(): Int = 45
  def aParameterlessFunction: Int = 45

  // functions can be recursive
  def stringConcatenation(s: String, n: Int): String =
    if (n==0) "" else s + stringConcatenation(s, n-1)

  val scalax3 = stringConcatenation("Scala", 3)

  // "void" functions
  def aVoidFunction(aString: String): Unit = println(aString)

  def computeDoubleStringWithSideEffect(aString: String): String = {
    aVoidFunction(aString) // Unit
    aString + aString // meaningful value
  } // discouraging side effects

  def aBigFunction(n: Int): Int = {
    def aSmallerFunction(a: Int, b: Int) = a + b

    aSmallerFunction(n, n+1)
  }

  /**
   * Exercises:
   * 1. Greeting function (name, age)
   * 2. Factorial function 1*2*...
   * 3. Fibonacci function 1 1 2 3 5 8 13 21 -> fib(1) = 1 fib(2) = 1 fib(3) = 2
   * 4. Tests if a number is prime
   * @param args
   */

  // 1. Greeting function (name, age)
  def greeting(name: String, age: Int) = s"Hi, my name is $name and I am $age years old"
  def factorial(n: Int): Int = if(n==0) 1 else n*factorial(n-1)
  def fibonacci(n: Int): Int = if(n<=1) 1 else fibonacci(n-1) + fibonacci(n-2)

  def countDivisors(x: Int, divisorCandidate: Int): Int = {
    if(divisorCandidate==1) 1 // 1 is divisor for everybody
    else if (x % divisorCandidate == 0) 1 + countDivisors(x, divisorCandidate-1) // divisorCandidate is divisor of x
    else countDivisors(x, divisorCandidate-1)
  }

  def isPrime(x: Int): Boolean = {
    countDivisors(x, x) == 2
  }

  def isPrimeDanielMyProposedFix(x: Int): Boolean = {
    def isPrimeUntil(y: Int): Boolean = {
      if (y<=1) true
      else x%y != 0 && isPrimeUntil(y-1)
    }

    x != 1 && isPrimeUntil(x/2)
  }

  def isPrimeDanielOfficial(n: Int): Boolean = {
    def isPrimeUntil(t: Int): Boolean =
      if (t <= 1) true
      else n % t != 0 && isPrimeUntil(t - 1)

    isPrimeUntil(n / 2)
  }

  def main(args: Array[String]): Unit = {
    println(scalax3)

    println(factorial(5))
    println(fibonacci(6))

    aVoidFunction("void function side effect")
    println(computeDoubleStringWithSideEffect("Scalax2+side effect"))
    println(isPrimeDanielOfficial(1))
    println(isPrimeDanielOfficial(2))
    println(isPrimeDanielOfficial(3))
    println(isPrimeDanielOfficial(4))
    println(isPrimeDanielOfficial(5))
    println(isPrimeDanielOfficial(6))
    println(isPrimeDanielOfficial(7))
    println(isPrimeDanielOfficial(8))
    println(isPrimeDanielOfficial(9))
    println(isPrimeDanielOfficial(10))
    println(isPrimeDanielOfficial(11))



  }
}
