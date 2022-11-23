package com.rockthejvm.part4power

import com.rockthejvm.practice.*

object AllThePatterns {
  object MySingleton

  // 1 - constants
  val someValue: Any = "Scala"
  val constants = someValue match {
    case 42 => "a number"
    case "Scala" => "THE Scala"
    case true => "The truth"
    case MySingleton => "A singleton object"
  }

  // 2 - match anything
  val matchAnythingVar = 2 + (3 match
    case something => s"I've matched anything, it's $something")

  val matchAnything = someValue match {
    case _ => "I can match anything at all"
  }

  // 3 - tuples
  val aTuple = (1,4)
  val matchTuple = aTuple match {
    case (1, somethingElse) => s"A tuple with 1 and $somethingElse"
    case (somethingElse, 2) => "A tuple with 2 as its second field"
  }

  // PM structures can be NESTED

  val nestedTuple = (1, (2, 3))
  val matchNestedTuple = nestedTuple match
    case (_, (2, v)) => "A nested tuple ..."

  // 4 - case classes
  val aList: LList[Int] = Cons(1, Cons(2, Empty()))
  val matchList = aList match {
    case Empty() => "an empty list"
    case Cons(head, Cons(_, tail)) => s"a non-empty list starting with $head"
  }

  val anOption: Option[Int] = Option(2)
  val matchOption = anOption match
    case None => "an empty option"
    case Some(value) => s"non-empty, got $value"

  // 5 - list patterns
  val aStandardList = List(1,2,3,43)
  val matchStandardList = aStandardList match {
    // can have a variable no of args in the deconstruction pattern
    case List(1, _, _, _) => "List with 4 elements, first is 1"
    case List(1, _*) => "list starting with 1"
    case List(1, 2, _) :+ 42 => "list ending in 42"
    // infix pattern -> VERY expressive
    case 1 :: tail => "deconstructed list"
  }

  // 6 - type specifiers
  val unknown: Any = 2
  val matchTyped = unknown match {
    case anInt: Int => s"I matched an Int, I can add 2 to it: ${anInt + 2}"
    case aString: String => "I matched a string"
    case _: Double => "I matched a double I don't care about"
  }

  // 7 - name binding
  val bindingNames = aList match {
    case Cons(head, rest @ Cons(_, tail)) => s"Can use $rest"
  }

  // 8 - chained patterns ???
  /*
  JavaVirtualMachines/openjdk-18.0.2/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=63228:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath /Users/alex/projects/scala-3-beginners/target/scala-3.0.0/classes:/Users/alex/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.5/scala-library-2.13.5.jar:/Users/alex/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.0.0/scala3-library_3-3.0.0.jar com.rockthejvm.part4power.AllThePatterns
  Exception in thread "main" java.lang.ExceptionInInitializerError
    at com.rockthejvm.part4power.AllThePatterns.main(AllThePatterns.scala)
  Caused by: scala.MatchError: [1 2] (of class com.rockthejvm.practice.Cons)
    at com.rockthejvm.part4power.AllThePatterns$.<clinit>(AllThePatterns.scala:76)
  * */
  val multiMatch = aList match {
    case Empty() | Cons(0, _) => "an empty list to me"
    case _ => "anything else"
  }

  // 9 - if guards ???
  /*
  /Users/alex/Library/Java/JavaVirtualMachines/openjdk-18.0.2/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=63302:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath /Users/alex/projects/scala-3-beginners/target/scala-3.0.0/classes:/Users/alex/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.5/scala-library-2.13.5.jar:/Users/alex/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/scala-lang/scala3-library_3/3.0.0/scala3-library_3-3.0.0.jar com.rockthejvm.part4power.AllThePatterns
  Exception in thread "main" java.lang.ExceptionInInitializerError
    at com.rockthejvm.part4power.AllThePatterns.main(AllThePatterns.scala)
  Caused by: scala.MatchError: [1 2] (of class com.rockthejvm.practice.Cons)
    at com.rockthejvm.part4power.AllThePatterns$.<clinit>(AllThePatterns.scala:89)
    ... 1 more
  */
  val secondElementSpecial = aList match {
    case Cons(_, Cons(specialElement, _)) if specialElement > 5
    => "second element is big enough"
    case _ => "anything else"
  }

  /**
   * Example
   */
  val aSimpleInt = 45
  val isEven_bad = aSimpleInt match {
    case n if n % 2 == 0 => true
    case _ => false
  }

  // heavy anti-pattern
  val isEven_bad_v2 = if (aSimpleInt % 2 == 0) true else false
  val isEven_v3 = aSimpleInt % 2 == 0

  /**
   * Exercise (trick)
   */
  val numbers = List[Int](1,2,3,4)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "a list of strings"
    case listOfInts: List[Int] => "a list of numbers"
  }

  // PM runs at runtime
  /* In order for the proper type to be matched the JVM bytecode(that the scala compiler generates)
   will have to inspect the unknown variable (from case 6) for its real type at runtime via reflection
   - reflection feature on the JVM - inspects value for its real type at runtime, because
    compiler could not detect it beforehand
   - the real type of a variable and all these pattern matching are ALWAYS done via REFLECTION
   - The generic types are erased at Runtime:
    - List[Int] => List
    - List[String] => List
    - Function1[Int, String] => Function1
   */


  def main(args: Array[String]): Unit = {
    println(matchAnythingVar)
    println(matchAnything)

    println(isEven_bad)
    println(isEven_bad_v2)
    println(isEven_v3)

    println(s"Numbers match List[Int] output. numbersMatch = $numbersMatch")
  }
}
