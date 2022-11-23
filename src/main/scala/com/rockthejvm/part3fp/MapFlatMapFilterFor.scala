package com.rockthejvm.part3fp

object MapFlatMapFilterFor {

  // standard list
  val aList = List(1,2,3) // [1] -> [2] -> [3] -> Nil // [1,2,3]
  val firstElement = aList.head
  val estOfElements = aList.tail

  // map
  val anIncrementedList = aList.map(_ + 1)

  // filter
  val onlyOddNumbers = aList.filter(_ % 2 != 0)

  // flatMap
  val toPair = (x: Int) => List(x, x+1)
  val aFlatMappedList = aList.flatMap(toPair) // [1,2, 2,3, 3,4]

  // All the possible combinations for all the elements of those lists, in the format "1a - black"
  val numbers = List(1,2,3,4)
  val chars = List('a', 'b', 'c', 'd')
  val colors = List("black", "white", "red")

//  def combineLists(numbers: List[Int], chars: List[Char], colors: List[String], accumulator: List[String]): List[String] = {
//    if (numbers.isEmpty) {
//      accumulator
//    } else {
//      if (chars.isEmpty) accumulator :: combineLists(numbers.tail, chars, colors, accumulator)
//      else {
//        if (colors.isEmpty) accumulator :: combineLists(numbers, chars.tail, colors, accumulator)
//        else {
//          val elem = s"${numbers.head}${chars.head} - ${colors.head}"
//          accumulator :: combineLists(numbers, chars, colors.tail, accumulator)
//        }
//      }
//    }
//  }
//  val cartesianProduct = combineLists(numbers, chars, colors, List.empty)

  val combinations = numbers.withFilter(_ % 2 == 0).flatMap(number => chars.flatMap(char => colors.map(color => s"$number$char - $color")))

  // for-comprehension = IDENTICAT to flatMap + map chains
  val combinationsFor = for {
    number <- numbers if number % 2 == 0 // generator
    char <- chars
    color <- colors
  } yield s"$number$char - $color" // an EXPRESSION

  // for-comprehension with Unit (with side-effects)
  // if foreach

  /**
   * Exercises:
   * 1. LList supports for-comprehension? Modify it so it does, if the case
   * 2. A small collection of AT MOST ONE element - Maybe[A]
   *  - map
   *  - flatMap
   *  - filter
   *  - withFilter -> if I want to support for-comprehension -> so that is the hint for point 1.
   *    -> to implement withFilter - identical to filter, I assume
   */


  def main(args: Array[String]): Unit = {
    numbers.foreach(println)
    for {
      num <- numbers
    } println(num)

    println(combinations)
    println(combinationsFor)

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
