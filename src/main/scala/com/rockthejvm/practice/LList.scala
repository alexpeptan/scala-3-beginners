package com.rockthejvm.practice

import scala.annotation.tailrec
import scala.jdk.Accumulator

import math.Fractional.Implicits.infixFractionalOps
import math.Integral.Implicits.infixIntegralOps
import math.Numeric.Implicits.infixNumericOps

// singly linked list
// [1, 2, 3] = [1] -> [2] -> [3] -> |
  abstract class LList[A] {
  def head: A
  def tail: LList[A]
  def isEmpty: Boolean
  def add(element: A): LList[A] = Cons(element, this)

  def reverse(): LList[A]
  def map[B](transformer: A => B): LList[B]
//  def filter(predicate: Predicate[A]): LList[A]
  def filter(predicate: A => Boolean): LList[A]
  def flatMap(transformer: A => LList[A]): LList[A]

  def foreach(f: A => Unit): Unit
  def sort(compare: (A, A) => Int): LList[A]
  def zipWith[B, T](list: LList[T], zip: (A, T) => B): LList[B]
  def foldLeft[B](start: B)(foldOperation: (A, B) => B): B

  def withFilter(predicate: A => Boolean): LList[A] = filter(predicate) // to enable for-comprehension
}

  case class Empty[A]() extends LList[A] {
  override def head: A = throw new NoSuchElementException
  override def tail: LList[A] = throw new NoSuchElementException

  override def isEmpty: Boolean = true
//  override def toString: String = "[]" // seems optional

  override def map[B](transformer: A => B): LList[B] = Empty()
  override def reverse(): LList[A] = this

  override def filter(predicate: (A) => Boolean): LList[A] = this
  override def flatMap(transformer: A => LList[A]): LList[A] = this

  override def foreach(f: A => Unit): Unit = ()

  override def sort(compare: (A, A) => Int): LList[A] = this
  override def zipWith[B, T](list: LList[T], zip: (A, T) => B): LList[B] = {
    if (!list.isEmpty) throw new IllegalArgumentException("Empty list zipping with non-empty list")
    else Empty()
  }
  override def foldLeft[B](start: B)(foldOperation: (A, B) => B): B = start
}

case class Cons[A](head: A, tail: LList[A]) extends LList[A] {
    override def isEmpty: Boolean = false
    override def add(element: A): LList[A] = {
      Cons[A](element, this)
    }

  // CCs give us a toString implementation, but it's too primitive - verbose
  override def toString: String = {
    @tailrec
    def concatElements(remainder: LList[A], accumulator: String): String = {
      if (remainder.isEmpty) accumulator
      else concatElements(remainder.tail, s"$accumulator ${remainder.head}")
    }

    s"[${concatElements(tail , s"$head")}]" // apparently imbricated string interpolations - interesting
  }

  override def reverse(): LList[A] = {
    @tailrec
    def reverseRemainder(remainder: LList[A], accumulator: LList[A]): LList[A] = {
      if (remainder.isEmpty) accumulator
      else reverseRemainder(remainder.tail, accumulator.add(remainder.head))
    }

    reverseRemainder(this, Empty()) // conserve elements order
  }

  // Needs to apply the transformation on all elements from the LList[A] and return a new LList[B]
  override def map[B](transformer: A => B): LList[B] = {
    @tailrec
    def applyTransformer(remainderList: LList[A], accumulator: LList[B]): LList[B] = {
      if (remainderList.isEmpty) accumulator
      else applyTransformer(remainderList.tail, accumulator.add(transformer(remainderList.head)))
    } // it will be in reverse order -> adding at the beginning of the list -> will do a reverse in @tailrec style

    applyTransformer(this, Empty())
  }

  override def filter(predicate: A => Boolean): LList[A] = {
    @tailrec
    def applyPredicate(remainingList: LList[A], accumulator: LList[A]): LList[A] = {
      if (remainingList.isEmpty) accumulator
      else applyPredicate(remainingList.tail,
        if (predicate(remainingList.head)) accumulator.add(remainingList.head)
        else accumulator)
    }

    applyPredicate(this, Empty()).reverse() // conserve elements order
  }

  override def flatMap(transformer: A => LList[A]): LList[A] = {
    @tailrec
    def flatten(remainingList: LList[A], accumulator: LList[A]): LList[A] = {
      if (remainingList.isEmpty) accumulator
      else flatten(remainingList.tail, accumulator.add(remainingList.head))
    }

    @tailrec
    def applyFlatMap(remainingList: LList[A], accumulator: LList[A]): LList[A] = { // invariant: accumulator "sorted" assuming llist is initially sorted
      if (remainingList.isEmpty) accumulator
      else {
        val transformedHeadElement = transformer(remainingList.head)
        val accumulatorPlusNewElements = flatten(accumulator.reverse(), transformedHeadElement)
        applyFlatMap(remainingList.tail, accumulatorPlusNewElements)
      }
    }

    applyFlatMap(this, Empty())
  }

  /** - foreach(A => Unit): Unit
   * [1,2,3].foreach(x => println(x))
   * */
  override def foreach(f: A => Unit): Unit = {
    // if (!this.isEmpty) { // Handled in Empty class
    f(head)
    this.tail.foreach(f)
  }

  /** - sort((A, A) => Int): LList[A]
   * [3,4,1,2].sort((x, y) => x - y) = [1,2,3,4]
   * */
  override def sort(compare: (A, A) => Int): LList[A] = {

    // not @tailrec
    def insert(x: A, list: LList[A]/*, compare: (A, A) => Int*/): LList[A] = {
      if (list.isEmpty) Cons(x, Empty())
      else if (compare(x, list.head) > 0) Cons(list.head, insert(x, list.tail/*, compare*/))
      else Cons(x, list)
    }

    // insertion sort
    @tailrec
    def insertionSort(/*compare: (A, A) => Int, */originalList: LList[A], accumulator: LList[A]): LList[A] = {
      if (originalList.isEmpty) accumulator
      else insertionSort(/*compare, */originalList.tail, insert(originalList.head, accumulator: LList[A]/*, compare*/))
    }

    insertionSort(/*compare, */this, Empty())

    // Daniel's implementation:
//    val sortedTail = tail.sort(compare)
//    insert(head, sortedTail)
  }

  /**
   * - zipWith[B](LList[A], (A, A) => B): LList[B]
   * [1,2,3].zipWith([4,5,6], (x, y) => x * y) = [1 * 4, 2 * 5, 3 * 6] = [4,10,18]
   */
  override def zipWith[B, T](list: LList[T], zip: (A, T) => B): LList[B] = {
    // this.isEmpty already handled in Empty class
    if (list.isEmpty) throw new UnsupportedOperationException("Lists need to have the same size")

    Cons(zip(head, list.head), tail.zipWith(list.tail, zip))
  }

  /**
   * - foldLeft[B](start: B)((A, B) => B): B
   * [1,2,3,4].foldLeft[Int](0)(x + y) = 10
   * 0 + 1 = 1
   * 1 + 2 = 3
   * 3 + 3 = 6
   * 6 + 4 = 10
   */
  override def foldLeft[B](start: B)(foldOperation: (A, B) => B): B = {
    // this.isEmpty does not need to be checked -> handled in Empty class
    tail.foldLeft(foldOperation(head, start))(foldOperation)
  }
}

/**
Exercise: LList extension
    1.  Generic trait Predicate[T] with a little method test(T) => Boolean
    2.  Generic trait Transformer[A, B] with a method transform(A) => B
    3.  LList:
        - map(transformer: Transformer[A, B]) => LList[B]
        - filter(predicate: Predicate[A]) => LList[A]
        - flatMap(transformer from A to LList[B]) => LList[B]
        class EvenPredicate extends Predicate[Int]
        class StringToIntTransformer extends Transformer[String, Int]
        [1,2,3].map(n * 2) = [2,4,6]
        [1,2,3,4].filter(n % 2 == 0) = [2,4]
        [1,2,3].flatMap(n => [n, n+1]) => [1,2, 2,3, 3,4]
 */

//trait Predicate[T] {
//  def test(element: T): Boolean
//}
//
//class EvenPredicate extends Predicate[Int] {
//  override def test(element: Int) =
//    element % 2 == 0
//}

//class Multipleof31Predicate extends Predicate[Int] {
//  override def test(element: Int) = element % 31 == 0
//}

//trait Transformer[A, B] {
//  def transform(element: A): B
//}

//class Doubler extends Transformer[Int, Int] {
//  override def transform(element: Int): Int = element * 2
//}

//class DoublerList extends Transformer[Int, LList[Int]] {
//  override def transform(element: Int): LList[Int] =
//    Cons[Int](element, Cons[Int](element + 1, Empty()))
//}

object LList {
  def find[A](list: LList[A], predicate: A => Boolean): A = {
    if (list.isEmpty) throw new NoSuchElementException()

    if (predicate(list.head)) list.head
    else find(list.tail, predicate)
  }
}

object LListTest {
  def main(args: Array[String]): Unit = {
    //    println(s"My empty list: $myList_v0")
    //    println(s"myList.head: ${myList_v0.head}")
    //    println(s"myList.tail: ${myList_v0.tail}")
    //    println(s"myList isEmpty: ${myList_v0.isEmpty}")
    //    val myList_v1 = myList_v0.add(4)
    //    println(s"myList_v1 isEmpty: ${myList_v1.isEmpty}")
    //    println(s"List with one element myList_v1: ${myList_v1}")
    //    val myList_v2 = myList_v1.add(3)
    //    println(s"myList_v2 isEmpty: ${myList_v2.isEmpty}")
    //    println(s"List with two elements myList_v2: ${myList_v2}")
    //    val myList_v3 = myList_v2.add(1)
    //    println(s"myList_v3 isEmpty: ${myList_v3.isEmpty}")
    //    println(s"List with 3 elements myList_v3: ${myList_v3}")

    val empty = Empty[Int]()
    println(empty.isEmpty)
    println(empty)

    val first3Numbers = Cons(1, Cons(2, Cons(3, Empty())))
    println(first3Numbers.isEmpty)
    println(first3Numbers.head)
    println(first3Numbers)

    val first3Numbers_v2 = empty.add(1).add(2).add(3)
    println(first3Numbers_v2)
    println(first3Numbers_v2.isEmpty)
    println(first3Numbers_v2.reverse())

    val someStrings = Cons("Dog", Cons("Cat", Empty()))
    println(someStrings)
    println(someStrings.reverse())

    val transformedList = first3Numbers_v2.map[Int](a => a * 2)
    println(transformedList)

    //    val tripleIntAnonymousTransformer = new Transformer[Int, Int] { // Abstract classes
    //      override def transform(element: Int): Int = 3 * element
    val tripleIntAnonymousTransformer: Int => Int = a => 3 * a



    // LEAVE THIS FOR ANOTHER TIME :)
    //      infix def *(Int): Int
    //      element => element * 3 // need to remember how to use that * on generic types
    //      value * is not a member of Object with com.rockthejvm.practice.Transformer[Int, Int] {...} & Object with
    //      com.rockthejvm.practice.Transformer[Int, Int]
    //      {...}, but could be made available as an extension method.
    //
    //        One of the following imports might make progress towards fixing the problem:
    //
    //      import math.Fractional.Implicits.infixFractionalOps
    //      import math.Integral.Implicits.infixIntegralOps
    //      import math.Numeric.Implicits.infixNumericOps

    val transformedListViaAnonymousTransformer = first3Numbers_v2.map[Int](tripleIntAnonymousTransformer)
    println(transformedListViaAnonymousTransformer)

    val evenElementsFilteredOut = transformedListViaAnonymousTransformer.filter(/*new EvenPredicate*/ a => a % 2 == 0)
    println(evenElementsFilteredOut)

    val oddElementsfilteredOutAnonymousPredicate = transformedListViaAnonymousTransformer.filter(
      element => !(element % 2 == 0)
      //    new Predicate[Int] { // expanded version
      //      override def test(element: Int): Boolean = !(element % 2 == 0)
      //    }
    )
    println(oddElementsfilteredOutAnonymousPredicate)

    val testValues = Cons(1, Cons(5, Cons(10, Cons(15, Empty()))))
    val flatMapWithExpansionTransformer = testValues.flatMap(a => Cons[Int](a, Cons[Int](a + 1, Empty())))
    println(flatMapWithExpansionTransformer)

    val firstEvent = LList.find(flatMapWithExpansionTransformer, a => a % 2 == 0) //new EvenPredicate)
    println(firstEvent)

    //    val firstMultipleOf31 = LList.find(flatMapWithExpansionTransformer, new Multipleof31Predicate) // throws NSException
    //    println(firstMultipleOf31)

    try {
      val firstMultipleOf31 = LList.find(flatMapWithExpansionTransformer, _ % 31 == 0) // throws NSException
      println(firstMultipleOf31)
    } catch {
      case e: NoSuchElementException => println("Just thrown & caught NoSuchElementException")
    }

    //    val firstGreaterThan10 = LList.find(flatMapWithExpansionTransformer, new Predicate[Int] {
    //      override def test(element: Int): Boolean = element > 10
    //    })
    val firstGreaterThan10 = LList.find(flatMapWithExpansionTransformer, _ > 10)
    println(firstGreaterThan10)

    val array = flatMapWithExpansionTransformer
    array.foreach(print) // using methods with 1 argument instead of a 1 argument function value

    val sorted: LList[Int] = array.sort((x: Int, y: Int) => x - y)
    println(sorted)
    val sorted_2: LList[Int] = array.sort((x: Int, y: Int) => y - x)
    println(sorted_2)

    val l1 = Cons(1, Cons(2, Empty()))
    val l2 = Cons(4, Cons(5, Empty()))
    val l3 = Cons(70, Cons(40, Empty()))

    val sum: (Int, Int) => Int = _ + _
    println(l1.zipWith(l2, sum))
    println(l2.zipWith(l3, sum))
    println(l1.zipWith(l3, sum))

    val foldExperiment = l1.foldLeft(10)(sum)
    println(foldExperiment)
    println(Empty[Int]().foldLeft(133))

    // able to do filtering BECAUSE WE HAVE map and flatMap IMPLEMENTED
    for {
      elem <- array if elem % 2 == 0 // generator -> able to use filtering because withFilter is defined in LList
    } print(s"$elem ")
  }
}
