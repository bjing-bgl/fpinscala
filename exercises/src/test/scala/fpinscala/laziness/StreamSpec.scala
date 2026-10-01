package fpinscala.laziness

import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 5: laziness - Stream
 *
 * These tests encode the expected behavior of the exercises in
 * `Stream.scala`. They will fail until you implement the `???` stubs.
 */
class StreamSpec extends AnyFunSuite {

  // Exercise 5.1: toList is not in the stub, so we verify via take/toList-style
  // conversions using the implemented helpers where possible.

  // Exercise 5.2: take and drop
  test("take returns the first n elements") {
    assert(Stream(1, 2, 3, 4, 5).take(2).toListViaFold == List(1, 2))
  }

  test("drop skips the first n elements") {
    assert(Stream(1, 2, 3, 4, 5).drop(2).toListViaFold == List(3, 4, 5))
  }

  // Exercise 5.3: takeWhile
  test("takeWhile returns the leading elements that satisfy the predicate") {
    assert(Stream(1, 2, 3, 4, 1).takeWhile(_ < 3).toListViaFold == List(1, 2))
  }

  // Exercise 5.4: forAll
  test("forAll checks every element against the predicate") {
    assert(Stream(2, 4, 6).forAll(_ % 2 == 0))
    assert(!Stream(2, 3, 6).forAll(_ % 2 == 0))
  }

  // Exercise 5.6: headOption
  test("headOption returns the first element if present") {
    assert(Stream(1, 2, 3).headOption == Some(1))
    assert(Stream.empty[Int].headOption == None)
  }

  // Exercise 5.9: from
  test("from produces an infinite increasing stream") {
    assert(Stream.from(5).take(3).toListViaFold == List(5, 6, 7))
  }

  // Exercise 5.11: unfold
  test("unfold builds a stream from a seed") {
    val counting = Stream.unfold(0)(s => Some((s, s + 1)))
    assert(counting.take(4).toListViaFold == List(0, 1, 2, 3))
  }

  // Exercise 5.13 (startsWith is 5.14): startsWith
  test("startsWith reports whether one stream is a prefix of another") {
    assert(Stream(1, 2, 3).startsWith(Stream(1, 2)))
    assert(!Stream(1, 2, 3).startsWith(Stream(2, 3)))
  }

  // Helper so these tests don't depend on an un-stubbed toList.
  // Uses the provided foldRight to materialize a Stream into a List.
  implicit class StreamToList[A](s: Stream[A]) {
    def toListViaFold: List[A] = s.foldRight(List.empty[A])((a, acc) => a :: acc)
  }
}
