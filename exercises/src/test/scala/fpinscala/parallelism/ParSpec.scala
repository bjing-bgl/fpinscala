package fpinscala.parallelism

import java.util.concurrent.Executors
import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 7: parallelism - Par
 *
 * `Par.scala` provides the core combinators; the exercises in this chapter
 * mostly extend the API (e.g. the `ParOps` syntax class) and are developed
 * further in `Nonblocking.scala`. These tests pin down the provided
 * combinators so the suite stays green as you flesh out the exercises.
 */
class ParSpec extends AnyFunSuite {

  val es = Executors.newFixedThreadPool(4)

  test("unit runs to its value") {
    assert(Par.run(es)(Par.unit(42)).get == 42)
  }

  test("map2 combines two Par values") {
    val p = Par.map2(Par.unit(2), Par.unit(3))(_ + _)
    assert(Par.run(es)(p).get == 5)
  }

  test("map transforms a Par value") {
    val p = Par.map(Par.unit(10))(_ + 1)
    assert(Par.run(es)(p).get == 11)
  }

  test("fork evaluates in the pool and preserves the value") {
    val p = Par.fork(Par.unit(7))
    assert(Par.run(es)(p).get == 7)
  }

  test("sortPar sorts a Par of List") {
    val p = Par.sortPar(Par.unit(List(3, 1, 2)))
    assert(Par.run(es)(p).get == List(1, 2, 3))
  }

  test("Examples.sum sums an IndexedSeq") {
    assert(Examples.sum(IndexedSeq(1, 2, 3, 4)) == 10)
  }
}
