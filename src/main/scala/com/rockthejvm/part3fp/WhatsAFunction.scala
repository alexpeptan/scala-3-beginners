package com.rockthejvm.part3fp

object WhatsAFunction {

  // FP: functions as "first class" citizens
  // JVM

  trait MyFunction[A, B] {
    def apply(arg: A): B
  }

  val doubler = new MyFunction[Int, Int] {
    override def apply(arg: Int) = 2 * arg
  }

  val meaningOfLife = 42
  val meaningDoubled = doubler(meaningOfLife) // same as doubler.apply(meaningOfLife)

  // function types
  var doublerStandard = new Function[Int, Int] {
    override def apply(arg: Int): Int = 2 * arg
  }

  var meaningDoubled_v2 = doublerStandard(meaningOfLife)

  // all functions are instances of FUnctionX with apply methods
  val adder = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }
  val anAddition = adder(2, 33)

  // (Int, String, Double, Boolean) => Int == Function4[Int, String, Double, Boolean, Int]
//  val aFourArgFunction = new Function4[Int, String, Double, Boolean, Int] {
//    override def apply(v1: Int, v2: String, v3: Double, v4: Any): Int = ???
//  }

  /**
   * Exercises:
   * 1. A function that takes 2 strings and concatenates them
   * 2. Replace Predicate/Transformer with the appropriate function types if necessary
   * 3. Define a function that takes an Int as an argument and returns ANOTHER FUNCTION as a result
   */

  def concat = new Function2[String, String, String] {
    override def apply(v1: String, v2: String): String = v1 + v2
  }

  def concat_2: (String, String) => String = (a, b) => a + b

  def intToFunction(a: Int): Function1[Int, Int] = {
    if (a > 0) a => a + 1
    else a => -a + 1
  }

  val resultedFunctionFromPositiveVal = intToFunction(7)
  val resultedFunctionFromNegativeVal = intToFunction(-7)

//  val superAdder = new (Int => (Int => Int)) {
//    override def apply(x: Int) = {
//        val sum: Int => Int = x + _
//        new (Int => Int) {
//          override def apply(y: Int) = sum(y)
//        }
//    }
//    //    override def apply(x: Int) = ((Int => Int) = x + _)
//  }

//  val increment: Int => Int = _ + 1
//  val superIncrement: Int => (Int => Int) = +(_, identity[Int])

//  val superAdder: (Int => (Int => Int)) = _ + (Int => Int = _)
//    override def apply(x: Int) =
//      val sum: Int => Int = x + _
//        (Int => Int) = x + _
//        override def apply(y: Int) = sum(y)
//      }
    //    override def apply(x: Int) = ((Int => Int) = x + _)
//  }

  val superAdder_v2 = (x: Int) => (y: Int) => x + y // these are families of functions! Aha!
  val adding2 = superAdder_v2(2) //  (y: Int) => 2 + y
  val addingInvocation = adding2(43) // 45
  val addingInvocation_v2 = superAdder_v2(2)(43) // same

  def main(args: Array[String]): Unit = {
    println(meaningDoubled)
    println(meaningDoubled_v2)
    println(anAddition)
    println(concat("Ana ", "are mere"))
    println(concat("Ana ", "are mere"))
    println(resultedFunctionFromPositiveVal(42))
    println(resultedFunctionFromNegativeVal(-112))

//    val superAdderUsage = superAdder(2)
//    println(superAdderUsage)
//    val superAdderUsageLvl2 = superAdderUsage(44)
//    println(superAdderUsageLvl2)
//    val superAdderCurrying = superAdder(33)(44)
//    println(superAdderCurrying)

//    println(increment(123))
//    println(superIncrement(3)(4))
    println(superAdder_v2(3)(44))
  }
}
