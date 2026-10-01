package fpinscala.state

import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 6: state - RNG / State / Machine
 *
 * These tests encode the expected behavior of the exercises in
 * `State.scala`. They will fail until you implement the `???` stubs.
 */
class StateSpec extends AnyFunSuite {

  val rng: RNG = RNG.Simple(42)

  // Exercise 6.1: nonNegativeInt
  test("nonNegativeInt always returns a non-negative Int") {
    val (n, _) = RNG.nonNegativeInt(rng)
    assert(n >= 0)
    // Running from the same seed is deterministic.
    assert(RNG.nonNegativeInt(rng)._1 == n)
  }

  // Exercise 6.2: double in [0, 1)
  test("double returns a value in [0, 1)") {
    val (d, _) = RNG.double(rng)
    assert(d >= 0.0 && d < 1.0)
  }

  // Exercise 6.3: intDouble / doubleInt / double3
  test("intDouble and doubleInt produce the paired values") {
    val ((i, d), _) = RNG.intDouble(rng)
    val ((d2, i2), _) = RNG.doubleInt(rng)
    assert(d >= 0.0 && d < 1.0)
    assert(d2 >= 0.0 && d2 < 1.0)
    // intDouble and doubleInt from the same seed yield mirrored components.
    assert(i == i2)
  }

  test("double3 returns three doubles in [0, 1)") {
    val ((a, b, c), _) = RNG.double3(rng)
    assert(List(a, b, c).forall(d => d >= 0.0 && d < 1.0))
  }

  // Exercise 6.4: ints
  test("ints generates a list of the requested length") {
    val (xs, _) = RNG.ints(5)(rng)
    assert(xs.length == 5)
  }

  // Exercise 6.6: map2
  test("map2 combines two Rand values") {
    val r = RNG.map2(RNG.unit(1), RNG.unit(2))(_ + _)
    assert(r(rng)._1 == 3)
  }

  // Exercise 6.7: sequence
  test("sequence combines a list of Rand into a Rand of list") {
    val r = RNG.sequence(List(RNG.unit(1), RNG.unit(2), RNG.unit(3)))
    assert(r(rng)._1 == List(1, 2, 3))
  }

  // Exercise 6.8: flatMap
  test("flatMap chains Rand computations") {
    val r = RNG.flatMap(RNG.unit(10))(a => RNG.unit(a + 1))
    assert(r(rng)._1 == 11)
  }

  // Exercise 6.10: State map / map2 / flatMap
  test("State.map transforms the result") {
    val s = State[Int, Int](n => (n, n + 1)).map(_ * 2)
    assert(s.run(5) == (10, 6))
  }

  test("State.map2 combines two states threading the state through") {
    val inc = State[Int, Int](n => (n, n + 1))
    val combined = inc.map2(inc)(_ + _)
    assert(combined.run(0) == (1, 2)) // first yields 0 (state->1), second yields 1 (state->2)
  }

  test("State.flatMap chains stateful computations") {
    val inc = State[Int, Int](n => (n, n + 1))
    val s = inc.flatMap(a => State[Int, Int](n => (a + n, n)))
    assert(s.run(0) == (1, 1))
  }

  // Exercise 6.11: simulateMachine
  test("simulateMachine processes coins and turns") {
    // 4 candies, 10 coins, start locked. Insert coin, turn, insert coin, turn...
    val inputs = List(Coin, Turn, Coin, Turn, Coin, Turn, Coin, Turn)
    val ((coins, candies), _) =
      State.simulateMachine(inputs).run(Machine(locked = true, candies = 5, coins = 10))
    // 4 candies dispensed -> 1 candy left, 14 coins.
    assert(candies == 1)
    assert(coins == 14)
  }
}
