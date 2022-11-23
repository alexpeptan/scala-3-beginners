package com.rockthejvm.part2oop

object Enums {

  enum Permissions { // sealed data type - cannot be extended
    case READ, WRITE, EXECUTE, NONE // cannot instantiate objects - can only use these instances

    // add fields/methods
    def openDocument(): Unit = {
      if (this == READ) println("Opening document")
      else println("Reading not allowed")
    }
  }

  val somePermissions = Permissions.EXECUTE

  // constructor with args
  enum PermissionsWithBits(bits: Int) {
    case READ extends PermissionsWithBits(4) // 100
    case WRITE extends PermissionsWithBits(2) // 010
    case EXECUTE extends PermissionsWithBits(1) // 001
    case NONE extends PermissionsWithBits(0) // 000
  }

  object PermissionsWithBits {
    def fromBits(bits: Int): PermissionsWithBits = // whatever
      PermissionsWithBits.NONE
  }

  // standard API
  val somePermissionsOrdinal = somePermissions.ordinal
  val allPermissions = PermissionsWithBits.values // array of all possible values of the enum
  val readPermission: Permissions = Permissions.valueOf("READ") // Permissions.READ

  def main(args: Array[String]): Unit = {
    somePermissions.openDocument()
    println(somePermissionsOrdinal)

    println(allPermissions)
    println(runtime.ScalaRunTime.replStringOf(allPermissions, 20))
    println(readPermission)
  }
}
