package fpinscala.monads

import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 11: monads - Monad / Id
 *
 * NOTE: The `Monad` companion object eagerly initializes several `val`s to
 * `???` (parMonad, optionMonad, listMonad, ...). Referencing ANY member of
 * that object forces its initializer, which throws until EVERY one of those
 * `val`s is implemented. To keep these tests useful before then, we build a
 * local `Monad` instance here and exercise the derived combinators
 * (sequence, traverse, replicateM, compose) that are the real exercises
 * 11.3/11.4/11.6. These will fail (NotImplementedError) until you implement
 * those `def` stubs in the `Monad` trait. `Id` (11.17) is tested directly.
 */
class MonadSpec extends AnyFunSuite {

  // A local Option monad so we don't force `Monad` object initialization.
  val optionMonad: Monad[Option] = new Monad[Option] {
    def unit[A](a: => A): Option[A] = Some(a)
    def flatMap[A, B](ma: Option[A])(f: A => Option[B]): Option[B] = ma.flatMap(f)
  }

  // Exercise 11.3: sequence
  test("sequence collects Somes or yields None") {
    assert(optionMonad.sequence(List(Some(1), Some(2), Some(3))) == Some(List(1, 2, 3)))
    assert(optionMonad.sequence(List(Some(1), None, Some(3))) == None)
  }

  // Exercise 11.3: traverse
  test("traverse maps and sequences") {
    assert(optionMonad.traverse(List(1, 2, 3))(a => Some(a * 2)) == Some(List(2, 4, 6)))
  }

  // Exercise 11.4: replicateM
  test("replicateM repeats a monadic value n times") {
    assert(optionMonad.replicateM(3, Some(1)) == Some(List(1, 1, 1)))
    assert(optionMonad.replicateM(3, None: Option[Int]) == None)
  }

  // Exercise 11.6: compose (Kleisli)
  test("compose composes two Kleisli arrows") {
    val f = (a: Int) => Some(a + 1)
    val g = (b: Int) => Some(b * 2)
    assert(optionMonad.compose(f, g)(3) == Some(8))
  }

  // Exercise 11.7/11.8: _flatMap (flatMap in terms of compose) and join
  test("_flatMap behaves like flatMap") {
    assert(optionMonad._flatMap(Some(2))(a => Some(a + 1)) == Some(3))
  }

  test("join flattens nested monadic values") {
    assert(optionMonad.join(Some(Some(5))) == Some(5))
    assert(optionMonad.join(Some(None: Option[Int])) == None)
  }

  // Exercise 11.17: Id monad (a standalone case class, safe to touch directly)
  test("Id.map and Id.flatMap operate on the wrapped value") {
    assert(Id(2).map(_ + 1) == Id(3))
    assert(Id(2).flatMap(a => Id(a * 10)) == Id(20))
  }
}
