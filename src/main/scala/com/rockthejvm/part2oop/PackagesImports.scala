package com.rockthejvm.part2oop

import com.rockthejvm.part2oop.PhysicsConstants.PLANK

val meaningOfLife = 42
val computeMyLife: String = "Scala"

object PackagesImports { // top-level

  // packages = folders

  // fully qualified name
  val aList: com.rockthejvm.practice.LList[Int] = com.rockthejvm.practice.Empty() // throws NotImplementedError

  // import -> recognized only below the import, not in the whole file
  import com.rockthejvm.practice.*
  val aListOfIntegers: LList[Int] = Empty()

  // importing under an alias
  import java.util.{List as JList} // List from Java
//  val aJavaList: JList[Int] = ???

  // import everything
  import com.rockthejvm.practice.*
//  val aPredicate: Predicate[Int] = ???

  // import multiple symbols
  import PhysicsConstants.{SPEED_OF_LIGHT, EARTH_GRAVITY}
  val c = SPEED_OF_LIGHT

  // import everything, but something
  object PlayingPhysics {
    import PhysicsConstants.{PLANK as _, *}
//    val plank = PLANK
  }

  import com.rockthejvm.part2oop.* // import mol and computeMyLife
  val mol = meaningOfLife

  // default imports
  // scala.*, scala.Predef.*, java.lang.*

  // exports
  class PhysicsCalculator {
    import PhysicsConstants.*
    def computePhotonEnergy(wavelength: Double): Double =
      PLANK / wavelength
  }

  object ScienceApp {
    val physicsCalculator = new PhysicsCalculator

    export physicsCalculator.computePhotonEnergy

    def computeEquivalentMass(wavelength: Double) =
      computePhotonEnergy(wavelength) / (SPEED_OF_LIGHT * SPEED_OF_LIGHT)
  }

  def main(args: Array[String]): Unit = {

  }
}

object PhysicsConstants {
  // constants
  val SPEED_OF_LIGHT = 299792458
  val PLANK = 6.62e-34 // scientific notation
  val EARTH_GRAVITY = 9.8
}
