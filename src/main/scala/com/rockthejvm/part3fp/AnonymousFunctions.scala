package com.rockthejvm.part3fp

object AnonymousFunctions {

  // instances of FunctionN
  val doubled: Int => Int = new Function[Int, Int] {
    override def apply(a: Int) = a * 2
  }

  // lambdas = anonymous function instances
  val doubled_v2: Int => Int = x => x * 2 // identical
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b // new Function2[Int, Int] { override def apply... }
  //zero-arg functions
  val justDoSomething: () => Int = () => 45
  val anInvocation = justDoSomething()

  // alt syntax with curly braces
  val stringToInt = { (str: String) =>
    // implementation: code block
    str.toInt
  }

  // shortest lambdas
  val doubler_v4: Int => Int = _ * 2 // x => x * 2
  val adder_v3: (Int, Int) => Int = _ + _
  // each _ is a different argument, you can't reuse them

  /**
   * Exercises:
   * 1. Replace all FunctionN instantiations with lambdas in LList implementation
   * 2. Rewrite the "special" adder from WhatsAFunction using lambdas
   */

  // type inference
  val doubler_v3: Int => Int = x => x * 2 // type inferred by compiler
  val adder_v2: (Int, Int) => Int = (x, y) => x + y

  def main(args: Array[String]): Unit = {
    println(justDoSomething)
    println(justDoSomething())
  }
}
