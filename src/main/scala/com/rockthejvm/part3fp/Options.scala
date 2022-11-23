package com.rockthejvm.part3fp

import java.util.Random

object Options {

  // options = "collections" with at most one value
  val anOption: Option[Int] = Option(42)
  val anEmptyOption: Option[Int] = Option.empty

  // alt version
  val aPresentValue: Option[Int] = Some(4)
  val anEmptyOption_v2: Option[Int] = None // empty option - proper substitute for this type

  // "standard" API
  val isEmpty = anOption.isEmpty
  val innerValue = anOption.getOrElse(92)
  val anotherOption = Option(46)
  val anChainedOption = anEmptyOption orElse anotherOption

  // map, flatMap, filter, for
  val anIncrementedOption = anOption.map(_ + 1) // Some(43)
  val aFilteredOption = anIncrementedOption.filter(_ % 2 == 0) // None
  val aFlatMappedOption = anOption.flatMap(value => Option(value * 10)) // Some(420)

  // WHY options: work with unsafe API
  def unsafeMethod(): String = null
  def fallbackMethod(): String = "some valid value"

  // usecase for orElse
  val stringLengthOption = Option(unsafeMethod()).orElse(Option(fallbackMethod()))

  // defensive style
  val stringLength = if (unsafeMethod() == null) -1 else unsafeMethod().length

  // option-style: no need for null checks
  val stringLengthOption_v2 = Option(unsafeMethod()).map(_.length)

  // DESIGN
  def betterUnsafeMethod(): Option[String] = None
  def betterFallbackMethod(): Option[String] = Some("a valid result")
  val betterChain = betterUnsafeMethod() orElse betterFallbackMethod()

  // example: Map.get
  val phoneBook = Map(
    "Daniel" -> 1234
  )
  val marysPhoneNumber = phoneBook.get("Mary") // None
  // no need to crash, check for nulls, or Mary is present in the map

  /**
   * Exercise
   *  Get the host and port from the config map
   *   try to open a connection
   *   print "Conn successful"
   *   or "Conn failed"
   */

  val config: Map[String, String] = Map(
    "host" -> "176.45.32.1",
    "port" -> "8081"
  )

  class Connection {
    def connect(): String = "Connection successful"
  }

  object Connection {
    val random = new Random()

    def apply(host: String, port: String): Option[Connection] = {
      if(random.nextBoolean()) Some(new Connection)
      else None
    }
  }

  // My version
  val host: String = config.get("host").getOrElse("Default host")
  val port: String = config.get("port").getOrElse("Default port")
  val connection = Connection(host, port)
  val connectionStatus: String = connection.map(_.connect()).getOrElse("Connection failed")

  // Daniel's version:
  val host_v2 = config.get("host")
  val port_v2 = config.get("port")
  val connection_v2 = host_v2.flatMap(h => port_v2.flatMap(p  => Connection(h, p)))
  val connectionStatus_v2 = connection_v2.map(_.connect())

  // Daniel's version v3:
  val connStatus_v3 = for {
    h <- config.get("host")
    p <- config.get("port")
    conn <- Connection(h, p)
  } yield conn.connect()


  def main(args: Array[String]): Unit = {
    println(connectionStatus)
    println(connectionStatus_v2.getOrElse("Connection failed"))
    println(connStatus_v3.getOrElse("Connection failed"))
  }
}
