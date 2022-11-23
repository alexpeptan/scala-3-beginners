package com.rockthejvm.part3fp

import com.rockthejvm.part3fp.Options.Connection

import scala.util.{Failure, Random, Success, Try}

object HandlingFailure {

  // Try =  potentially failed computation
  val aTry: Try[Int] = Try(42)
  val aFailedTry: Try[Int] = Try(throw new RuntimeException)

  // alt construction
  val aTry_v2: Try[Int] = Success(42)
  val aFailure: Try[Int] = Failure(new RuntimeException)

  // main API
  val checkSuccess = aTry.isSuccess
  val checkFailure = aTry.isFailure
  val aChain = aFailedTry.orElse(aTry)

  // map, flatMap, filter
  val anIncrementedTry = aTry.map(_ + 1)
  val aFlatMappedTry = aTry.flatMap(mol => Try(s"My meaning of life is $mol"))
  val aFilteredTry = aTry.filter(_ % 2 == 0) // Success(42). Otherwise NoSuchElementException exception - if filter fails

  // WHY: avoid unsafe APIs that can THROW exceptions
  def unsafeMethod(): String =
    throw new RuntimeException("No string for you, buster!")

  // defensive: try-catch-finally -> very hard to read, hard to debug and hard to handle all edge cases
  val stringLenDeffensive = try {
    val aString = unsafeMethod()
  } catch {
    case e: RuntimeException => -1
  }

  // purely functional
  val stringLengthPure = Try(unsafeMethod()).map(_.length).getOrElse(-1)

  // DESIGN
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException("No string for you, buster!"))
  def betterBackupMethod(): Try[String] = Success("Scala")
  val stringLengthPure_v2 = betterUnsafeMethod().map(_.length)
  val betterSafeChain = betterUnsafeMethod().orElse(betterBackupMethod()).map(_.length)

  /**
   * Exercise
   *  obtain a connection
   *  then fetch the URL
   *  then print the resulting html
   */
  val host = "localhost"
  val port = "8081"
  val myDesiredURL = "rockthejvm.com/home"

  class Connection {
    val random = new Random()

    def get(url: String): String = {
      if (random.nextBoolean()) "<html>Success</html>"
      else throw new RuntimeException("Cannot fetch page right now.")
    }

    def getSafe(url: String): Try[String] = Try(get(url))
  }

  object HttpService {
    val random = new Random()

    def getConnection(host: String, port: String): Connection =
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException("Cannot access host/port combination.")

    def getConnectionSafe(host: String, port: String): Try[Connection] =
      Try(getConnection(host, port))
  }

  // My version
  val connection: Try[Connection] = Try(HttpService.getConnection(host, port))
  val resultingHTML: Try[String] = Try(connection.get.get(myDesiredURL))

  // Daniel: defensive style
  val finalHTML = try {
    val conn = HttpService.getConnection(host, port)
    val html = try {
      conn.get(myDesiredURL)
    } catch {
      case e: RuntimeException => s"<html>${e.getMessage}</html>"
    }
  } catch {
    case e: RuntimeException => s"<html>${e.getMessage}</html>"
  }

  // Daniel: purely functional approach
  val maybeConn = Try(HttpService.getConnection(host, port))
  val maybeHTML = maybeConn.flatMap(conn => Try(conn.get(myDesiredURL)))
  // Collapsing a Try instance to a single value by processing both the exception and the successful value with the fold method
  val finalResult = maybeHTML.fold(e => s"<html>${e.getMessage}</html>", s => s)

  // for-comprehension
  val maybeHtml_v2 = for {
    conn <- HttpService.getConnectionSafe(host, port)
    html <- conn.getSafe(myDesiredURL)
  } yield html
  val finalResult_v2 = maybeHtml_v2.fold(e => s"<html>${e.getMessage}</html>", identity)

  def main(args: Array[String]): Unit = {
    println(resultingHTML.getOrElse("Cannot access host/port combination."))
    println(finalResult)
    println(finalResult_v2)
  }
}
