package com.rockthejvm.part4power

import scala.util.Random

object PatternMatching {
  // switch on steroids
  val random = new Random
  val aValue = random.nextInt(100)

  val description = aValue match {
    case 1 => "the first"
    case 2 => "the second"
    case 3 => "the third"
    // Unless I define a default case I risk getting:
    // Exception in thread "main" java.lang.ExceptionInInitializerError ...
    //   Caused by: scala.MatchError: 60 (of class java.lang.Integer)
    case _ => s"the ${aValue}th"
  }

  // decompose values
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 16)

  // put the most specific match first!!!!
  val greeting = bob match
    case Person(n, a) if a < 18=> s"Hi, I'm $n and I'm $a years old."
    case Person(n, a) => s"Hello, I'm $n and I'm between ${a - random.nextInt(5)} and ${a + random.nextInt(5)} years old."
    case _ => "I don't know who I am."

  /*
    Patterns are matched in order: put the most specific patterns first!
    What if no cases match? -> MatchError
    What's the type returned? -> The lowest common ancestorof all types on the RHS ofeach branch.
   */

  // PM on sealed hierarchies
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Cat(meowStyle: String) extends Animal

  val anAnimal: Animal = Dog("Terra Nova")

  val animalPM = anAnimal match
    case Dog(breed) => s"I've detected a $breed dog."
    case Cat(meow) => "I've detected a cat"

  /** Exercise
   * show(Sum(Number(2), Number(3))) = "2 + 3"
   * show(Sum(Sum(Number(2), Number(3)), Number(4)) = "2 + 3 + 4"
   * show(Prod(Sum(Number(2), Number(3)), Number(4)) = "(2 + 3) * 4"
   * show(Sum(Prod(Number(2), Number(3)), Number(4)) = "2 * 3 + 4"
   * */

  sealed trait Expr {
    def show(): String
  }

  case class Number(n: Int) extends Expr {
    override def show(): String = n.toString
  }
  case class Sum(e1: Expr, e2: Expr) extends Expr {
    override def show(): String = s"${e1.show()} + ${e2.show()}"
  }

  case class Prod(e1: Expr, e2: Expr) extends Expr
  {
    override def show(): String = {
      this match {
        case Prod(_: Sum, _: Sum)
        => s"(${e1.show()}) * (${e2.show()})"
        case Prod(_: Sum, _: Number) | Prod(_: Sum, _: Prod)
        => s"(${e1.show()}) * ${e2.show()}"
        case Prod(_: Number, _: Sum) | Prod(_: Prod, _: Sum)
        => s"${e1.show()} * (${e2.show()})"
        case Prod(_: Number, _: Prod) | Prod(_: Prod, _: Number) | Prod(_: Prod, _: Prod) | _
        => s"${e1.show()} * ${e2.show()}"
      }
    }
  }

  def prodFirstExprParentheses(e1: Expr, e2: Expr) = s"(${show(e1)}) * ${show(e2)}"
  def prodBothExprParentheses(e1: Expr, e2: Expr) = s"(${show(e1)}) * ${show(e2)}"
  def prodSecondExprParentheses(e1: Expr, e2: Expr) = s"${e1.show()} * (${e2.show()})"
  def prodNoExprParentheses(e1: Expr, e2: Expr) = s"${e1.show()} * ${e2.show()}"
  def sumNoExprParentheses(e1: Expr, e2: Expr) = s"${e1.show()} + ${e2.show()}"


  // Daniel's (apparent) approach, my implementation - function (not being part of any class - still feels like a new/strange concept to me)
  def show(expr: Expr): String = expr match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => sumNoExprParentheses(e1, e2)
    case Prod(e1: Sum, e2: Sum) => prodBothExprParentheses(e1, e2)
    case Prod(e1: Sum, e2: Number) => prodFirstExprParentheses(e1, e2)
    case Prod(e1: Sum, e2: Prod) => prodFirstExprParentheses(e1, e2)
    case Prod(e1: Number, e2: Sum) => prodSecondExprParentheses(e1, e2)
    case Prod(e1: Prod, e2: Sum) => prodSecondExprParentheses(e1, e2)
    case Prod(e1: Number, e2: Prod) => prodNoExprParentheses(e1, e2)
    case Prod(e1: Prod, e2: Number) => prodNoExprParentheses(e1, e2)
    case Prod(e1: Prod, e2: Prod) => prodNoExprParentheses(e1, e2)
    case Prod(e1: Number, e2: Number) => prodNoExprParentheses(e1, e2)
  }

  // Daniel's actual implementation - recursive
  // - parentheses logic ISOLATED
  // - used RECURSIVELY WITHOUT extra methods - will come with practice ^^
  def show_2(expr: Expr): String = expr match {
    case Number(n) => s"$n"
    case Sum(left, right) => show_2(left) + " + " + show_2(right)
    case Prod(left, right) => { // I know I handle a term from a product -> extra context
      def maybeShowParantheses(innerExp: Expr): String = innerExp match {
        case Prod(_, _) => show_2(innerExp)
        case Number(_) => show_2(innerExp)
        case Sum(_, _) => s"(${show_2(innerExp)})" // term is a sum -> use parentheses -> the only case
      }

      maybeShowParantheses(left) + " * " + maybeShowParantheses(right)
    }
  }



  val number1 = Number(13)
  val sum1: Expr = Sum(Number(2), Number(3))
  val sum1_show = sum1.show()
  val sum2 = Sum(sum1, Number(4))
  val sum2_show = sum2.show()
  val prod1: Expr = Prod(sum2, Number(5))
  val prod2: Expr = Prod(Number(23), Number(5))
  val prod3: Expr = Prod(Number(5), sum2)
  val prod4: Expr = Prod(prod3, sum1)
  val prod5: Expr = Prod(sum2, prod3)
  val prod6: Expr = Prod(sum1, sum2)
  val prod7: Expr = Prod(prod1, prod2)
  val prod8: Expr = Prod(prod1, Number(5))
  val prod9: Expr = Prod(Number(5), prod1)

  def main(args: Array[String]): Unit = {
    println(description)
    println(greeting)
    println(animalPM)

//    println(number1)
//    println(sum1)
//    println(sum1_show)
//    println(sum2)
//    println(sum2_show)
//    println(prod1)
    println(prod1.show())
    println(show(prod1))
    println(show_2(prod1))
    println(prod2.show())
    println(show(prod2))
    println(show_2(prod2))
    println(prod3.show())
    println(show(prod3))
    println(show_2(prod3))
    println(prod4.show())
    println(show(prod4))
    println(show_2(prod4))
    println(prod5.show())
    println(show(prod5))
    println(show_2(prod5))
    println(prod6.show())
    println(show(prod6))
    println(show_2(prod6))
    println(prod7.show())
    println(show(prod7))
    println(show_2(prod7))
    println(prod8.show())
    println(show(prod8))
    println(show_2(prod8))
    println(prod9.show())
    println(show(prod9))
    println(show_2(prod9))


  }
}
