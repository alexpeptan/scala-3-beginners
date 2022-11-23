package com.rockthejvm.part1basics

object ValuesAndTypes {
  val meaningOfLife: Int = 42

  // reassignment is not allowed
  //  meaningOfLife = 45

  // type inference
  val anInteger = 67

  // common types
  val aBoolean: Boolean = false
  val aChar: Char = 'c'
  val anInt: Int = 43443 // 4 bytes
  val aShort: Short = 3334 // 2 bytes
  val aLong: Long = 423432423432423L // 8 bytes
  val aFloat: Float = 2.3f // 4 bytes
  val aDouble: Double = 3.14 // 8 bytes

  // string
  val aString: String = "Scala"

  def main(args: Array[String]): Unit = {

  }
}
