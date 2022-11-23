package com.rockthejvm.practice

import scala.annotation.tailrec

object TuplesMapsExercises {
  /**
   * Social Network: Map[String, Set[String]]
   * Friend relationships are MUTUAL
   *
   * - add a person to the network
   * - remove a person from the network
   * - add a friend relationship (network, a, b) -> and symetrical
   * - unfriend
   *
   * - number of friends of a person
   * - who has the most friends
   * - how many people have no friends
   * + if there is a social connection between two people
   *
   * There is a connection between Daniel and Tom
   * Daniel <-> Mary <-> Jane <-> Tom
   *
   */
  def addPerson(network: Map[String, Set[String]], newPerson: String): Map[String, Set[String]] = {
     if (!network.contains(newPerson)) network + (newPerson -> Set())
     else network
  }

  def removePerson(network: Map[String, Set[String]], newPerson: String): Map[String, Set[String]] = {
    // will also need to remove it from all his friend's lists
//    var cleanNetwork = network
//    for {
//      person <- network.keys
//    } yield {
//      cleanNetwork = unfriend(cleanNetwork, person, newPerson)
//    }
//    cleanNetwork - newPerson

    network.map(entry => (entry._1, entry._2 - newPerson)) - newPerson
//  network.map((_._1, _._2 - newPerson)) // cannot use _ compact form more than once per param
  }

  def addFriend(network: Map[String, Set[String]], person1: String, person2: String): Map[String, Set[String]] = {
    // use var to avoid using 4 local variables that allow me to handle newly created maps
    val networkWithPerson1 =
      if (!network.contains(person1)) addPerson(network, person1)
      else network
    val networkContainingBothPersons =
      if (!networkWithPerson1.contains(person2)) addPerson(networkWithPerson1, person2)
      else networkWithPerson1
    if (person1 == person2) throw UnsupportedOperationException("A person cannot be its own friend.")

//    networkContainingBothPersons
//      .updated(person1, networkContainingBothPersons(person1) + person2)
//      .updated(person2, networkContainingBothPersons(person2) + person1)

    // overwriting existing map entries
    networkContainingBothPersons
      + (person1 -> (networkContainingBothPersons(person1) + person2))
      + (person2 -> (networkContainingBothPersons(person2) + person1))
  }

  def unfriend(network: Map[String, Set[String]], person1: String, person2: String): Map[String, Set[String]] = {
    if (!network.contains(person1)) throw UnsupportedOperationException(s"$person1 is not part of the network")
    if (!network.contains(person2)) throw UnsupportedOperationException(s"$person2 is not part of the network")

//    network
//      .updated(person1, network(person1) - person2)
//      .updated(person2, network(person2) - person1)
      network + (person1 -> (network(person1) - person2)) + (person2 -> (network(person2) - person1))
  }

  def friendsNo(network: Map[String, Set[String]], person: String): Int = {
    if (!network.contains(person)) throw UnsupportedOperationException(s"$person is not part of the network.")
    network(person).size
  }

  def mostPopular(network: Map[String, Set[String]]): String = {
    var maxFriends = -1
    if (network.keys.isEmpty) throw UnsupportedOperationException(s"Social network is not social at all.")
    val starOfTheGroup: String = null
    for {
      person <- network.keys
    } yield {
      if (friendsNo(network, person) > maxFriends) {
        maxFriends = friendsNo(network, person)
      }
    }

    starOfTheGroup
  }

  def mostPopular_v2(network: Map[String, Set[String]]): String = {
    if (network.isEmpty) throw RuntimeException("Network is empty, no-one with most friends")

    val best = network.foldLeft(("", -1)) { (currentBest, newAssociation) =>
      // code block
      val currentMostPopularPerson = currentBest._1
      val currentMaxNetworkSize = currentBest._2

      val newPersonCandidate = newAssociation._1
      val newPersonNetworkSize = newAssociation._2.size

      if (newPersonNetworkSize > currentMaxNetworkSize) (newPersonCandidate, newPersonNetworkSize)
      else /*(currentMostPopularPerson, currentMaxNetworkSize)*/ currentBest // daaaah, this is the tuple variable
    }

    best._1
  }

  def numberOfLonelyOnes(network: Map[String, Set[String]]): Int =
    val filteredNetwork = network.view.filter((_, setOfFriends: Set[String]) => setOfFriends.isEmpty)
    val filteredToMap = filteredNetwork.toMap
    filteredToMap.size

  def numberOfLonelyOnes_v2(network: Map[String, Set[String]]): Int =
    network.count(entry => entry._2.isEmpty)
//    network.filter(entry => entry._2.isEmpty).size

  def connected(network: Map[String, Set[String]], person1: String, person2: String): Boolean = {
    def search(currentPerson: String, /*targetPerson: String, */visitedPersons: Set[String]): Boolean = {
      if (currentPerson == person2) return true

      for {
        friend <- network(currentPerson)
      } yield {
          if (!visitedPersons(friend) && search(friend, /*person2, */visitedPersons + friend))
            return true
      }

      false
    }

    search(person1, /*person2, */Set(person1))
  }

  /**
   * Making it tail recursive:
   * 1. need to call search last time in the call (only once)
   * 2. -> I cannot manually call search for all of currentPerson's friends - will have multiple calls
   * 3. -> Once per search call do a check on a candidate and somehow make another search call
   * 4. -> What collection do I do the check on? (some candidatesSet) -> will need to put it in the param list
   * 5. Will keep the visited concept
   * @param args
   */
  def connected_v2(network: Map[String, Set[String]], sourcePerson: String, targetPerson: String): Boolean = {
    @tailrec
    def search(candidates: Set[String], visited: Set[String]): Boolean = {
      if(candidates.contains(targetPerson)) return true
      if(candidates.isEmpty) return false

      // unless I ensure candidates is not empty I get: Exception in thread "main" java.util.NoSuchElementException: next on empty iterator
      val candidate = candidates.head // just pick one
      if (!visited(candidate)) search(candidates - candidate ++ network(candidate) -- visited, visited + candidate)
      else search(candidates - candidate -- visited, visited)
    }

    search(Set(sourcePerson), Set(sourcePerson))
  }


  def main(args: Array[String]): Unit = {
    val empty: Map[String, Set[String]] = Map()
    val john = "John"
    val mary = "Mary"
    val alex = "Alex"
    val bob = "Bob"
    val andrew = "Andrew"
    val susan = "Susan"
    val socialSusanJohnFriends = addFriend(addPerson(addPerson(empty, john), susan), susan, john)
//    socialn = addPerson(socialn, susan)
//    socialn = addFriend(socialn, john, susan)
    println(socialSusanJohnFriends)
    val complexNetwork = removePerson(
                          addPerson(
                            addPerson(
                              addFriend(
                                addFriend(socialSusanJohnFriends, john, mary),
                                alex, mary),
                              bob),
                            andrew),
                         susan)
//    socialn = addFriend(socialn, alex, mary)
//    socialn = addPerson(socialn, bob)
//    socialn = addPerson(socialn, andrew)
//    socialn = removePerson(socialn, susan)
    val lonelyPeople = numberOfLonelyOnes_v2(complexNetwork)
    println(s"We have $lonelyPeople lonely ones")
    println(s"Currently the most popular person is ${mostPopular_v2(complexNetwork)}")

    println(complexNetwork)
//    println(s"$john and $alex are ${if !connected(complexNetwork, john, alex) then "not " else ""}connected.")
//    println(s"$john and $bob are ${if !connected(complexNetwork, john, bob) then "not " else ""}connected.")
//    println(s"$john and $john are ${if !connected(complexNetwork, john, john) then "not " else ""}connected. Know thyself.")
//    println()
//
//    println(s"$john and $alex are ${if !connected_v2(complexNetwork, john, alex) then "not " else ""}connected.")
//    println(s"$john and $bob are ${if !connected_v2(complexNetwork, john, bob) then "not " else ""}connected.")
//    println(s"$john and $john are ${if !connected_v2(complexNetwork, john, john) then "not " else ""}connected. Know thyself.")
//
//
//    println(s"$john and Ana are ${if !connected_v2(complexNetwork, john, "Ana") then "not " else ""}connected.")
    println(s"Ana and $john are ${if !connected_v2(complexNetwork, "Ana", john) then "not " else ""}connected.")

//    val map: Map[String, String] = Map("ana" -> "x")
//    val diana = map("diana")
//    println(diana)
  }
}

