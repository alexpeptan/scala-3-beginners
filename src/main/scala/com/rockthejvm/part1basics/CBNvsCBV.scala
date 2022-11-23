package com.rockthejvm.part1basics

object CBNvsCBV {

  // CBV = call by value = arguments are evaluated before function invocation
  def aFunction(x: Int): Int = x*2
  val aComputation = aFunction(12 + 43)

  // CBN = call by name = arguments are passed LITERALLY, evaluated at every reference

  def aByNameFunction(arg: => Int): Int = arg*2
  val anotherComputation = aByNameFunction(12 + 43)

  def printTwiceByValue(x: Long): Unit = {
    println("Print by value: " + x)
    println("Print by value: " + x)
  }

  def printTwiceByName(x: => Long): Unit = {
    println(s"Print by value: ${x}")
    Thread.sleep(500)
    println(s"Print by value: ${x}")
  }

  def infinite(): Int = 1 + infinite()
  def printFirst(x: Int, y: => Int): Unit = println(x)

  def main(args: Array[String]): Unit = {
    println(aComputation)
    println(anotherComputation)
    println(printTwiceByValue(System.nanoTime()))
    println(printTwiceByName(System.nanoTime()))

//    println(infinite()) // crashes
//    printFirst(infinite(), 42) // crashes
    printFirst(42, infinite())
    // call by value params evaluation is delayed until it's needed
    // useful for handling dangerous expressions as late as possible / not at all
  }
}
