package fpinscala.parallelism

import java.util.concurrent.Executors
import org.scalatest.funsuite.AnyFunSuite

import Nonblocking._

/**
 * Chapter 7: parallelism - Nonblocking
 *
 * These tests encode the expected behavior of the exercises in
 * `Nonblocking.scala` (choiceN, flatMap, join, and friends). They will
 * fail until you implement the `???` stubs. The provided combinators
 * (unit, map, map2, fork, sequence) are also exercised as a baseline.
 */
class NonblockingSpec extends AnyFunSuite {

  val es = Executors.newFixedThreadPool(4)

  test("Par.unit and map (provided) work") {
    assert(Par.run(es)(Par.map(Par.unit(2))(_ + 1)) == 3)
  }

  test("Par.map2 (provided) combines values") {
    assert(Par.run(es)(Par.map2(Par.unit(2), Par.unit(3))(_ + _)) == 5)
  }

  test("Par.sequence (provided) collects results") {
    val p = Par.sequence(List(Par.unit(1), Par.unit(2), Par.unit(3)))
    assert(Par.run(es)(p) == List(1, 2, 3))
  }

  // Exercise 7.11: choiceN
  test("choiceN selects the nth alternative") {
    val p = Par.choiceN(Par.unit(1))(List(Par.unit("a"), Par.unit("b"), Par.unit("c")))
    assert(Par.run(es)(p) == "b")
  }

  // Exercise 7.11: choiceViaChoiceN
  test("choiceViaChoiceN picks between two options") {
    assert(Par.run(es)(Par.choiceViaChoiceN(Par.unit(true))(Par.unit("t"), Par.unit("f"))) == "t")
    assert(Par.run(es)(Par.choiceViaChoiceN(Par.unit(false))(Par.unit("t"), Par.unit("f"))) == "f")
  }

  // Exercise 7.12: choiceMap
  test("choiceMap looks up by key") {
    val p = Par.choiceMap(Par.unit("x"))(Map("x" -> Par.unit(1), "y" -> Par.unit(2)))
    assert(Par.run(es)(p) == 1)
  }

  // Exercise 7.13: chooser / flatMap
  test("flatMap chains a Par-producing function") {
    val p = Par.flatMap(Par.unit(10))(a => Par.unit(a + 5))
    assert(Par.run(es)(p) == 15)
  }

  // Exercise 7.14: join
  test("join flattens a nested Par") {
    val p = Par.join(Par.unit(Par.unit(99)))
    assert(Par.run(es)(p) == 99)
  }
}
