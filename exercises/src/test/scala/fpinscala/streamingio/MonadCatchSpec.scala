package fpinscala.streamingio

import org.scalatest.funsuite.AnyFunSuite

/**
 * Chapter 15: streamingio - MonadCatch
 *
 * `MonadCatch.task` is a `MonadCatch[Task]`, and `Task` is wired to the
 * `iomonad` package object whose `implicit val ioMonad = IO3.freeMonad[Par]`
 * is an eagerly-initialized exercise stub (`???`). Referencing `Task` (and
 * therefore `MonadCatch.task`) forces that initializer, which throws until
 * the free monad (Exercise 13.1) is implemented — this would abort the suite.
 *
 * So these tests are marked `ignore` until the iomonad free-monad exercises
 * are complete. After that, switch `ignore` to `test` and assert on the
 * instance, e.g. that `attempt` turns a failing Task into a `Right(Left(e))`.
 */
class MonadCatchSpec extends AnyFunSuite {

  ignore("MonadCatch[Task] provides attempt/fail (needs IO3.freeMonad, ex. 13.1)") {
    // pending implementation of the iomonad free-monad interpreter
  }
}
