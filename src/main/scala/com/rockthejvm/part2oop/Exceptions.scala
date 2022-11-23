package com.rockthejvm.part2oop

import scala.util.Random

object Exceptions {

  val aString: String = null
  // aString.length crashes with NPE

  // 1 - throw exceptions
//  val aWeirdValue: Int = throw new NullPointerException // returns Nothing -> proper replacement for any type

  // type Throwable -> anythiong that can crash the app
  //    Error, e.g. SOError, OOMError,
  //    Exception, e.g. NPException, NSEException, ... -> Here we should operate

  def getInt(withExceptions: Boolean) = {
    if (withExceptions) throw new RuntimeException("No int for you!")
    else 42
  }

  val potentialFail = try { // return lowest common ancestor
    // code that might fail
    getInt(true) // an Int
  } catch { // case order matters
    // most specific exceptions first
    case e: NullPointerException => 35 // since it is a RuntimeException -> in case of NPE this will not be reached -> will go on first case
    case e: RuntimeException => 54 // an Int
    // ...
  } finally {
    // executed no matter what
    // closing resources
    // Unit here
  }

  // custom exceptions
  class MyException extends RuntimeException {
    override def getMessage = "MY EXCEPTION"
  }

  val myException = new MyException
//  val throwingMyException = throw myException // any instance of the Throwable hierarchy

  /**
   * Exercises:
   * 1. Crash with SOError
   * 2. Crash with OOMError
   * 3. Find an element matching a predicate in LList
   *
   * @param args
   */

  def SOErrorGenerator(): Int = {
    SOErrorGenerator() + 1
  }

  def OOMErrorGenerator(s: String): String = {
    OOMErrorGenerator(s+s) // not allowed to use loops -> non-idiomatic. Tail recursive function does not seem to generate it either
  }

  def main(args: Array[String]): Unit = {
//    println(potentialFail)
//    val throwingMyException = throw myException // any instance of the Throwable hierarchy
//    SOErrorGenerator()
//    OOMErrorGenerator("Scala")
  }
}
