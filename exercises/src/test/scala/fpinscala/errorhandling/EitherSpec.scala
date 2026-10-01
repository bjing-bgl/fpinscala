package fpinscala.errorhandling

import org.scalatest.funsuite.AnyFunSuite

// Hide std library Either/Left/Right/Option just like the exercise file.
import scala.{Option => _, Either => _, Left => _, Right => _, _}

/**
 * Chapter 4: errorhandling - Either
 *
 * These tests encode the expected behavior of the exercises in
 * `Either.scala`. They will fail until you implement the `???` stubs.
 */
class EitherSpec extends AnyFunSuite {

  // Exercise 4.6: map, flatMap, orElse, map2
  test("map transforms a Right and leaves a Left unchanged") {
    assert((Right(2): Either[String, Int]).map(_ + 1) == Right(3))
    assert((Left("e"): Either[String, Int]).map(_ + 1) == Left("e"))
  }

  test("flatMap chains Either-producing functions") {
    assert((Right(2): Either[String, Int]).flatMap(a => Right(a + 1)) == Right(3))
    assert((Right(2): Either[String, Int]).flatMap(_ => Left("boom")) == Left("boom"))
  }

  test("orElse returns this Right or the alternative on Left") {
    assert((Right(1): Either[String, Int]).orElse(Right(9)) == Right(1))
    assert((Left("e"): Either[String, Int]).orElse(Right(9)) == Right(9))
  }

  test("map2 combines two Rights, short-circuiting on Left") {
    assert((Right(1): Either[String, Int]).map2(Right(2): Either[String, Int])(_ + _) == Right(3))
    assert((Right(1): Either[String, Int]).map2(Left("e"): Either[String, Int])(_ + _) == Left("e"))
  }

  // Exercise 4.7: sequence and traverse
  test("sequence collects all Rights or returns the first Left") {
    assert(Either.sequence(List(Right(1), Right(2), Right(3): Either[String, Int])) == Right(List(1, 2, 3)))
    assert(Either.sequence(List(Right(1), Left("e"), Right(3): Either[String, Int])) == Left("e"))
  }

  test("traverse maps and sequences in one pass") {
    assert(Either.traverse(List(1, 2, 3))(a => Right(a * 2): Either[String, Int]) == Right(List(2, 4, 6)))
    assert(Either.traverse(List(1, 2, 3))(a => if (a == 2) Left("e") else Right(a): Either[String, Int]) == Left("e"))
  }
}
