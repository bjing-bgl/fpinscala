package fpinscala.gettingstarted

import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 2: gettingstarted
 *
 * These tests encode the expected behavior of the exercises in
 * `GettingStarted.scala`. They will fail (throwing NotImplementedError)
 * until you replace the `???` stubs with working implementations.
 */
class GettingStartedSpec extends AnyFunSuite {

  // Exercise 2.1: fib
  test("fib returns the nth Fibonacci number (0-indexed, starting 0, 1)") {
    val expected = List(0, 1, 1, 2, 3, 5, 8, 13, 21, 34)
    val actual = (0 until expected.length).map(MyModule.fib).toList
    assert(actual == expected)
  }

  // Exercise 2.2: isSorted
  test("isSorted returns true for an ascending array") {
    assert(PolymorphicFunctions.isSorted[Int](Array(1, 2, 3, 4), _ > _))
  }

  test("isSorted returns false for an out-of-order array") {
    assert(!PolymorphicFunctions.isSorted[Int](Array(1, 3, 2, 4), _ > _))
  }

  test("isSorted returns true for empty and single-element arrays") {
    assert(PolymorphicFunctions.isSorted[Int](Array(), _ > _))
    assert(PolymorphicFunctions.isSorted[Int](Array(42), _ > _))
  }

  // Exercise 2.3: curry
  test("curry turns a 2-arg function into a chain of 1-arg functions") {
    val add = (a: Int, b: Int) => a + b
    assert(PolymorphicFunctions.curry(add)(3)(4) == 7)
  }

  // Exercise 2.4: uncurry
  test("uncurry turns a curried function back into a 2-arg function") {
    val add = (a: Int) => (b: Int) => a + b
    assert(PolymorphicFunctions.uncurry(add)(3, 4) == 7)
  }

  // Exercise 2.5: compose
  test("compose composes two functions") {
    val f = (b: Int) => b + 1
    val g = (a: Int) => a * 2
    assert(PolymorphicFunctions.compose(f, g)(10) == 21)
  }
}
