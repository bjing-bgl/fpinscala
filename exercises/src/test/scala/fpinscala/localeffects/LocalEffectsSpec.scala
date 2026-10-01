package fpinscala.localeffects

import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 14: localeffects - ST / STArray / quicksort
 *
 * `Mutable.quicksort` is provided and fully implemented. `Immutable.quicksort`
 * relies on the exercises `STArray.fill`, `Immutable.partition`, and
 * `Immutable.qs`, so the `Immutable` test will fail (NotImplementedError)
 * until you implement those `???` stubs.
 */
class LocalEffectsSpec extends AnyFunSuite {

  val unsorted = List(5, 3, 8, 1, 9, 2, 7)
  val sorted = unsorted.sorted

  // Provided baseline.
  test("Mutable.quicksort sorts a list") {
    assert(Mutable.quicksort(unsorted) == sorted)
    assert(Mutable.quicksort(Nil) == Nil)
  }

  // Exercise 14.1 (fill) + 14.2 (partition/qs) via Immutable.quicksort.
  test("Immutable.quicksort sorts a list using the ST monad") {
    assert(Immutable.quicksort(unsorted) == sorted)
    assert(Immutable.quicksort(Nil) == Nil)
  }

  // ST plumbing that is provided.
  test("ST run via runST returns pure results") {
    val prog = new RunnableST[Int] {
      def apply[S] = for {
        r <- STRef[S, Int](1)
        _ <- r.write(42)
        v <- r.read
      } yield v
    }
    assert(ST.runST(prog) == 42)
  }
}
