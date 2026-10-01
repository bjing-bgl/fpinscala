package fpinscala.iomonad

import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 13: iomonad - Monad
 *
 * `iomonad/Monad.scala` provides a general-purpose Monad trait with many
 * derived combinators (map, map2, replicateM, sequence_, foldM, etc.).
 * These are fully implemented, so we verify them against a simple concrete
 * instance (an Option monad).
 */
class MonadSpec extends AnyFunSuite {

  val optionMonad: Monad[Option] = new Monad[Option] {
    def unit[A](a: => A): Option[A] = Some(a)
    def flatMap[A, B](a: Option[A])(f: A => Option[B]): Option[B] = a.flatMap(f)
  }

  test("map is derived from flatMap/unit") {
    assert(optionMonad.map(Some(2))(_ + 1) == Some(3))
  }

  test("map2 combines two values") {
    assert(optionMonad.map2(Some(2), Some(3))(_ + _) == Some(5))
    assert(optionMonad.map2(Some(2), None: Option[Int])(_ + _) == None)
  }

  test("replicateM repeats n times") {
    assert(optionMonad.replicateM(3)(Some(1)) == Some(List(1, 1, 1)))
  }

  test("as replaces the result value") {
    assert(optionMonad.as(Some(1))("x") == Some("x"))
  }

  test("foldM folds a stream monadically") {
    val result = optionMonad.foldM(Stream(1, 2, 3))(0)((acc, a) => Some(acc + a))
    assert(result == Some(6))
  }
}
