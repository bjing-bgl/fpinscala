package fpinscala.datastructures

import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 3: datastructures - List
 *
 * These tests encode the expected behavior of the exercises in
 * `List.scala`. They will fail until you implement the `???` stubs.
 */
class ListSpec extends AnyFunSuite {

  // Exercise 3.1: the pattern match result `x`
  test("the pattern match binds x to 3") {
    assert(List.x == 3)
  }

  // Exercise 3.2: tail
  test("tail drops the first element") {
    assert(List.tail(List(1, 2, 3)) == List(2, 3))
  }

  // Exercise 3.3: setHead
  test("setHead replaces the first element") {
    assert(List.setHead(List(1, 2, 3), 9) == List(9, 2, 3))
  }

  // Exercise 3.4: drop
  test("drop removes the first n elements") {
    assert(List.drop(List(1, 2, 3, 4), 2) == List(3, 4))
    assert(List.drop(List(1, 2), 5) == Nil)
  }

  // Exercise 3.5: dropWhile
  test("dropWhile removes the leading elements that match") {
    assert(List.dropWhile[Int](List(1, 2, 3, 4), _ < 3) == List(3, 4))
  }

  // Exercise 3.6: init
  test("init returns all but the last element") {
    assert(List.init(List(1, 2, 3, 4)) == List(1, 2, 3))
  }

  // Exercise 3.9: length
  test("length counts the elements") {
    assert(List.length(List(1, 2, 3, 4)) == 4)
    assert(List.length(Nil) == 0)
  }

  // Exercise 3.10: foldLeft
  test("foldLeft folds from the left") {
    assert(List.foldLeft(List(1, 2, 3), 0)(_ + _) == 6)
    assert(List.foldLeft(List(1, 2, 3), "")(_ + _.toString) == "123")
  }

  // Exercise 3.18: map
  test("map applies f to every element") {
    assert(List.map(List(1, 2, 3))(_ + 1) == List(2, 3, 4))
  }
}
