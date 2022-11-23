package com.rockthejvm.part1basics

import scala.annotation.tailrec

object DefaultArgs {

  @tailrec
  def sumUntilTailrec(x: Int, accumulator: Int = 0): Int =
    if (x <= 0) accumulator
    else sumUntilTailrec(x - 1, x + accumulator)

  val sumUntil100 = sumUntilTailrec(100) // additional args passed automatically

  // when you use a function most of the time with the same value = default arguments
  def savePicture(dirPath: String, name: String, format: String = "jpg", width: Int = 1920, height: Int = 1080) =
    println(s"Saving picture in format $format in path $dirPath")

  def main(args: Array[String]): Unit = {
    println(sumUntil100)
    // default args are injected
    savePicture("users/alex/photos", "myPhoto")
    // pass explicit different arguments for fault args
    savePicture("users/alex/photos", "myPhoto", "png")
    // pass values after the default arguments
    savePicture("users/alex/photos", "myPhoto", width = 800, 600)
    // naming arguments allows passing them in different order
    savePicture("users/alex/photos", "myPhoto",  height = 600, width = 800)
  }
}
