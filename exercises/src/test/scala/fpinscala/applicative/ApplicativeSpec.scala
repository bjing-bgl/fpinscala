package fpinscala.applicative

import org.scalatest.funsuite.AnyFunSuite
import fpinscala.state._

/**
 * Chapter 12: applicative - Applicative / Monad / Traverse
 *
 * The `Applicative` trait methods (map2, apply, sequence, traverse, ...) are
 * the exercises. We test them through the provided `streamApplicative`
 * (which overrides `map2` concretely) and the provided `Monad.stateMonad`.
 * The `???` instances (eitherMonad, validationApplicative, Traverse.*) will
 * fail until implemented; add assertions as you complete them.
 */
class ApplicativeSpec extends AnyFunSuite {

  // Provided: streamApplicative overrides map2, so map2/map work on streams.
  test("streamApplicative.unit produces a constant stream") {
    assert(Applicative.streamApplicative.unit(7).take(3).toList == List(7, 7, 7))
  }

  test("streamApplicative.map2 zips pointwise") {
    val a = Stream(1, 2, 3)
    val b = Stream(10, 20, 30)
    val c = Applicative.streamApplicative.map2(a, b)(_ + _)
    assert(c.toList == List(11, 22, 33))
  }

  // Provided: stateMonad is fully implemented via State.
  test("Monad.stateMonad threads state through flatMap") {
    val m = Monad.stateMonad[Int]
    val prog = m.flatMap(State[Int, Int](s => (s, s + 1)))(a => m.unit(a + 100))
    assert(prog.run(5) == (105, 6))
  }

  test("Monad.stateMonad.map2 combines two stateful steps") {
    val m = Monad.stateMonad[Int]
    val inc = State[Int, Int](s => (s, s + 1))
    assert(m.map2(inc, inc)(_ + _).run(0) == (1, 2))
  }

  // Exercise 12.6 (validationApplicative), 12.20 (eitherMonad),
  // 12.13 (Traverse instances): implement the `???` members, then add
  // assertions such as:
  //
  //   assert(Monad.eitherMonad[String].unit(1) == Right(1))
}
