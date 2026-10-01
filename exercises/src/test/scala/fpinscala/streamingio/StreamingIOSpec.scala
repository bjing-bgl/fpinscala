package fpinscala.streamingio

import org.scalatest.funsuite.AnyFunSuite

import SimpleStreamTransducers.Process
import SimpleStreamTransducers.Process._

/**
 * Chapter 15: streamingio - StreamingIO
 *
 * The pure single-input transducer `Process` provides `apply`, `map`, `++`,
 * `flatMap`, and helpers `lift`, `filter`, `sum`. The exercises implement
 * `take`, `drop`, `takeWhile`, `count`, `mean`, `|>`, `zipWithIndex`, etc.
 * Provided combinators are verified as a baseline; the exercise-backed tests
 * will fail until you implement the `???` stubs.
 */
class StreamingIOSpec extends AnyFunSuite {

  // Provided: lift turns a function into a Process.
  test("lift maps every input") {
    val p: Process[Int, Int] = lift(_ + 1)
    assert(p(Stream(1, 2, 3)).toList == List(2, 3, 4))
  }

  // Provided: filter keeps matching inputs.
  test("filter keeps inputs matching the predicate") {
    val p: Process[Int, Int] = filter(_ % 2 == 0)
    assert(p(Stream(1, 2, 3, 4)).toList == List(2, 4))
  }

  // Provided: sum emits running totals.
  test("sum emits running totals") {
    assert(sum(Stream(1.0, 2.0, 3.0)).toList == List(1.0, 3.0, 6.0))
  }

  // Exercise 15.1: take / drop / takeWhile / dropWhile
  test("take emits the first n inputs") {
    assert(take[Int](2)(Stream(1, 2, 3, 4)).toList == List(1, 2))
  }

  test("drop skips the first n inputs") {
    assert(drop[Int](2)(Stream(1, 2, 3, 4)).toList == List(3, 4))
  }

  test("takeWhile emits while the predicate holds") {
    assert(takeWhile[Int](_ < 3)(Stream(1, 2, 3, 1)).toList == List(1, 2))
  }

  // Exercise 15.2: count
  test("count emits the running count") {
    assert(count[String](Stream("a", "b", "c")).toList == List(1, 2, 3))
  }

  // Exercise 15.3: mean
  test("mean emits the running mean") {
    assert(mean(Stream(1.0, 2.0, 3.0)).toList == List(1.0, 1.5, 2.0))
  }

  // Exercise 15.5: |> (composition)
  test("|> pipes one process into another") {
    val p = filter[Int](_ % 2 == 0) |> lift(_ * 10)
    assert(p(Stream(1, 2, 3, 4)).toList == List(20, 40))
  }

  // Exercise 15.6: zipWithIndex
  test("zipWithIndex pairs each output with its index") {
    val p = lift[String, String](identity).zipWithIndex
    assert(p(Stream("a", "b", "c")).toList == List(("a", 0), ("b", 1), ("c", 2)))
  }
}
