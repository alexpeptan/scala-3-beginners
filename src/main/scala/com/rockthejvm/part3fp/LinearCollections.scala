package com.rockthejvm.part3fp

import scala.util.Random

object LinearCollections {

  // Seq - well defined ordering + indexing
  def testSeq(): Unit = {
    val seq = Seq(4,3,2,1)
    // main API: index an element
    val thirdElement = seq.apply(2) // element 3
    // map/flatMap/filter/for
    val anIncrementedSequence = seq.map(_ + 1) // [5,4,3,2]
    val aFlatMappedSequence = seq.flatMap(x => Seq(x, x + 1)) // [4,5,3,4,2,3,1,2]
    val aFilteredSequence = seq.filter(_ % 2 == 0) // [4,2]

    // other methods
    val reversed = seq.reverse
    val concatenation = seq ++ Seq(5,6,7)
    val sortedSequence = concatenation.sorted
    val sum = seq.foldLeft(0)(_ + _) // 10
    val stringRep = seq.mkString(", ")
    val stringRep_v2 = seq.mkString("[", ",", "]") // useful for serializing a sequence to a stream

    println(seq)
    println(concatenation)
    println(sortedSequence)
    println(sum)
    println(stringRep)
    println(stringRep_v2)
  }

  // lists
  def testLists(): Unit = {
    val aList = List(1,2,3)
    // same API as seq
    val firstElement = aList.head
    val rest = aList.tail
    // appending and prepending
    val aBiggerList = 0 +: aList :+ 4
    val prepending = 0 :: aList // :: equivalent to Cons in our LList
    // utility method
    val scalax5 = List.fill(5)("Scala")  // List["Scala","Scala","Scala","Scala","Scala"]
  }

  // ranges -> they don't actually need to evaluate or contain all the elements inside
  def testRanges(): Unit = {
    val aRange = 1 to 10
    val aNonInclusiveRange = 1 until 4
    // same Seq API
    (1 to 10).foreach(x => print(s"$x "))
    aRange.foreach(x => print(s"$x | "))
    aNonInclusiveRange.foreach(_ => print("Scala "))
    println()
  }

  // arrays
  def testArrays(): Unit = {
    val anArray = Array(1,2,3,4,5,6) // int[] on the JVM
    // most seq APIs
    // arrays are not Seqs
    val aSeq: Seq[Int] = anArray.toIndexedSeq
    // arrays are mutable
    anArray.update(3, 30) // no new array is allocated
  }

  // vectors = fast Seqs for large amount of data
  def testVectors(): Unit = {
    val aVector: Vector[Int] = Vector(1,2,3,4,5)
    // the same Seq API



  }

  def smallBenchmark(): Unit = {
    val maxRuns = 1000
    val maxCapacity = 1000000

    def getWriteTime(collection: Seq[Int]): Double = {
      val random = new Random()
      val times = for {
        i <- 1 to maxRuns
      } yield {
        val index = random.nextInt(maxCapacity)
        val element = random.nextInt()
        val currentTime = System.nanoTime()
        val updatedCollection = collection.updated(index, element)
        System.nanoTime() - currentTime
      }

//      times.foldLeft(0L)(_ + _) * 1.0 / maxRuns -> definition of sum
      times.sum * 1.0 / maxRuns // * 1.0 to convert it to a Double
    }

    val numbersList = (1 to maxCapacity).toList
    val numbersVector = (1 to maxCapacity).toVector

    println(getWriteTime(numbersList))
    println(getWriteTime(numbersVector))
  }

  // sets
  def testSets(): Unit = {
    val aSet = Set(1,2,3,4,5,4) // no ordering guaranteed
    // equals + hashcode
    // main API: test if in the set
    val contains3 = aSet.contains(3) // true
    val contains3_v2 = aSet.apply(3) // same
    val contains3_v3 = aSet(3) // same
    // adding/removing
    val aBiggerSet = aSet + 4 // [1,2,3,4,5]
    val aSmallerSet = aSet - 4 // [1,2,3,5]
    // concatenation
    val anotherSet = Set(4,5,6,7,8)
    val muchBiggerSet = aSet.union(anotherSet)
    val muchBiggerSet_v2 = aSet ++ anotherSet // same
    val muchBiggerSet_v3 = aSet | anotherSet // same
    // difference
    val aDiffSet = aSet.diff(anotherSet)
    val aDIffSet_v2 = aSet -- anotherSet
    // intersection
    val anIntersection = aSet.intersect(anotherSet) // [4,5]
    val anIntersection_v2 = aSet & anotherSet // same
  }

  def main(args: Array[String]): Unit = {
    testSeq()
    testLists()
    testRanges()

    smallBenchmark()
  }
}
