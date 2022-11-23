package com.rockthejvm.part1basics

object Expressions {

  // expressions are structures that can be evaluated to a value
  val meaningOfLife = 40 + 2

  // mathematical expressions + - * / bitwise | & << >> >>>
  val mathExpressions = 2 + 3*4

  val equalityTest = 1==3

  // boolean expressions ! || &&
  val nonEqTest = !equalityTest

  // instructions vs expressions
  // expressions are evaluated, instructions are executed
  // we think in terms of expressions

  // ifs are expressions
  val aCondition = true
  val anIfExpression = if (aCondition) 45 else 99

  // code block
  val aCodeBlock = {
    // local value
    val localValue = 78
    // expressions...
    // last expression = value of the block
    /* like "return" */ localValue + 54
  }

  // everything is an expression
  // 1.
  val someVal = {
    2 < 3
  }

  // 2.
  val someOtherValue = {
    if (someVal) 34 else 44
    42
  }

  // 3.
  val yetAnotherVal = println("Scala")
  val theUnit: Unit = (5)
  def main(args: Array[String]): Unit = {
//    println(meaningOfLife)
//    println(anIfExpression)
//    println(if (aCondition) 45 else 99)

    println(someVal)
    println(someOtherValue)
    println(yetAnotherVal)
    println(theUnit)
  }
}
