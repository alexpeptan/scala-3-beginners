package com.rockthejvm.part3fp

abstract class Maybe[A] {
  def map[B](transformer: A => B): Maybe[B]
  def filter(predicate: A => Boolean): Maybe[A]
  def flatMap[B](transformer: A => /*B*/Maybe[B]): Maybe[B]
  def withFilter(predicate: A => Boolean): Maybe[A]
  def add(value: A): Maybe[A]
  def getValue(): A
  def isEmpty(): Boolean
}

class MaybeNot[A] extends Maybe[A] {
  override def map[B](transformer: A => B): Maybe[B] = MaybeNot[B]()
  override def filter(predicate: A => Boolean): Maybe[A] = this
  override def flatMap[B](transformer: A => /*B*/Maybe[B]): Maybe[B] = MaybeNot[B]()
  override def withFilter(predicate: A => Boolean): Maybe[A] = filter(predicate)
  override def add(value: A): Maybe[A] = Just(value)
  override def getValue(): A = throw new NoSuchElementException("Collection is empty")
  override def isEmpty(): Boolean = true
}

case class Just[A](head: A) extends Maybe[A] {
  override def map[B](transformer: A => B): Maybe[B] = Just(transformer(head))
  override def filter(predicate: A => Boolean): Maybe[A] =
    if (predicate(head)) this
    else MaybeNot()
  override def flatMap[B](transformer: A => /*B*/Maybe[B]): Maybe[B] = /*map(transformer)*/ transformer(head)
  override def withFilter(predicate: A => Boolean): Maybe[A] = filter(predicate)
  override def add(value: A): Maybe[A] = throw new UnsupportedOperationException(s"Maybe collection already has one element: $head")
  override def getValue(): A = head
  override def isEmpty(): Boolean = false
}

object MatbeTest {
  def main(args: Array[String]): Unit = {
    val x: Maybe[Int] = MaybeNot() // type inference -> know the variable type -> type safety(certainty)
    // -> and the generic type of the Empty in inferred, not the other way around
    //    val xValue = x.getValue() // throws exception Exception in thread "main" java.util.NoSuchElementException: Collection is empty
    val y = x.add(34)
    val z = x.add(3) // legit because x is still Empty
    //    val t = y.add(4) // will throw exception Exception in thread "main" java.lang.UnsupportedOperationException: Maybe collection already has one element: 34
    val yValue = y.getValue()
    println(yValue)

    val filteredX = y.filter(_ % 2 != 0)
    val isFilteredXEmpty = filteredX.isEmpty() // expect true
    println(isFilteredXEmpty)
    val filteredX_v2 = y.filter(_ % 2 != 1)
    val isFilteredXEmpty_v2 = filteredX_v2.isEmpty() // expect true
    println(isFilteredXEmpty_v2)

    val mappedY = y.map(_ / 3) // 11
    println(mappedY.getValue())

    val flatMappedY = y.flatMap((x: Int) => Just(x ^ 2)) // 34 = 32 + 2 = 100100. 34 ^ 2 = 100100 ^ 100 = 100000 = 32
    println(flatMappedY.getValue())
  }
}