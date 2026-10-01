package fpinscala.errorhandling

import org.scalatest.funsuite.AnyFunSuite

// Hide std library Option/Some/None/Either so these names resolve to the
// chapter's own types defined in Option.scala.
import scala.{Option => _, Some => _, None => _, Either => _, _}

/**
 * Chapter 4: errorhandling - Option
 *
 * These tests encode the expected behavior of the exercises in
 * `Option.scala`. They will fail until you implement the `???` stubs.
 */
class OptionSpec extends AnyFunSuite {

  // Typed `None` values avoid the compiler inferring `None.type`.
  def none[A]: Option[A] = None

  // Exercise 4.1: map, getOrElse, flatMap, orElse, filter
  test("map transforms the value inside a Some") {
    assert(Some(2).map(_ + 1) == Some(3))
    assert(none[Int].map(_ + 1) == none[Int])
  }

  test("getOrElse returns the value or the default") {
    assert(Some(2).getOrElse(0) == 2)
    assert(none[Int].getOrElse(0) == 0)
  }

  test("flatMap chains Option-producing functions") {
    assert(Some(2).flatMap(a => Some(a + 1)) == Some(3))
    assert(Some(2).flatMap(_ => none[Int]) == none[Int])
  }

  test("orElse returns this if defined, otherwise the alternative") {
    assert(Some(2).orElse(Some(9)) == Some(2))
    assert(none[Int].orElse(Some(9)) == Some(9))
  }

  test("filter keeps the value only when the predicate holds") {
    assert(Some(4).filter(_ % 2 == 0) == Some(4))
    assert(Some(3).filter(_ % 2 == 0) == none[Int])
  }

  // Exercise 4.2: variance
  test("variance computes the variance of a sequence") {
    assert(Option.variance(Seq(1.0, 2.0, 3.0)) == Some(2.0 / 3.0))
    assert(Option.variance(Seq()) == none[Double])
  }

  // Exercise 4.3: map2
  test("map2 combines two Options") {
    assert(Option.map2(Some(1), Some(2))(_ + _) == Some(3))
    assert(Option.map2(Some(1), none[Int])(_ + _) == none[Int])
  }

  // Exercise 4.4: sequence
  test("sequence turns a list of Options into an Option of list") {
    assert(Option.sequence(List(Some(1), Some(2), Some(3))) == Some(List(1, 2, 3)))
    assert(Option.sequence(List[Option[Int]](Some(1), none[Int], Some(3))) == none[List[Int]])
  }

  // Exercise 4.5: traverse
  test("traverse maps and sequences in one pass") {
    assert(Option.traverse(List(1, 2, 3))(a => Some(a * 2)) == Some(List(2, 4, 6)))
    assert(Option.traverse(List(1, 2, 3))(a => if (a == 2) none[Int] else Some(a)) == none[List[Int]])
  }
}
